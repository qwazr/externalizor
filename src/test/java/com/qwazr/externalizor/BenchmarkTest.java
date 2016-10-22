/**
 * Copyright 2016 Emmanuel Keller / QWAZR
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qwazr.externalizor;

import org.junit.Assert;
import org.junit.Test;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.*;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class BenchmarkTest {

	private final int TIME = 3;

	public class BenchResult {

		final String name;
		final Class<?> clazz;
		final float rate;
		final float avgSize;

		BenchResult(String name, Class<?> clazz, int counter, long totalSize, long totalTime) {
			this.name = name;
			this.clazz = clazz;
			this.avgSize = totalSize / counter;
			this.rate = counter * 1000 / totalTime;
		}

		final public String toString() {
			return name + " - " + clazz.getSimpleName() + " - Avg. size: " + avgSize + " Rate: " + rate;
		}
	}

	public String compare(BenchResult r1, BenchResult r2) {
		return "Size X : " + (r2.avgSize / r1.avgSize) * 100 + " Rate X : " + (r2.rate / r1.rate);
	}

	final static <T> byte[] write(final T object) {
		try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			try (final BufferedOutputStream buffered = new BufferedOutputStream(output)) {
				try (final SnappyOutputStream compressed = new SnappyOutputStream(buffered)) {
					try (final ObjectOutputStream objected = new ObjectOutputStream(compressed)) {
						objected.writeObject(object);
						objected.flush();
					}
				}
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T read(final byte[] bytes) {
		try (final ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
			try (final BufferedInputStream buffered = new BufferedInputStream(input)) {
				try (final SnappyInputStream compressed = new SnappyInputStream(buffered)) {
					try (final ObjectInputStream objected = new ObjectInputStream(compressed)) {
						return (T) objected.readObject();
					}
				}
			}
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> byte[] writeRaw(final T object) {
		try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			try (final BufferedOutputStream buffered = new BufferedOutputStream(output)) {
				try (final ObjectOutputStream objected = new ObjectOutputStream(buffered)) {
					objected.writeObject(object);
					objected.flush();
				}
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T readRaw(final byte[] bytes) {
		try (final ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
			try (final BufferedInputStream buffered = new BufferedInputStream(input)) {
				try (final ObjectInputStream objected = new ObjectInputStream(buffered)) {
					return (T) objected.readObject();
				}
			}
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> BenchResult benchmark(String name, Duration duration, Callable<T> callNewObject,
			Function<T, byte[]> serialize, Function<byte[], T> deserialize) throws Exception {

		System.gc();

		// Compute the final time
		long endTime = System.currentTimeMillis() + duration.toMillis();
		int counter = 0;
		long totalSize = 0;

		final long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() < endTime) {

			// Create a new object
			final T write = callNewObject.call();

			// Serialize
			final byte[] byteArray = serialize.apply(write);
			Assert.assertNotNull(byteArray);

			//Deserialize
			final T read = deserialize.apply(byteArray);
			Assert.assertNotNull(read);

			// Check equals
			Assert.assertEquals(write, read);

			totalSize += byteArray.length;
			counter++;
		}
		final long totalTime = System.currentTimeMillis() - startTime;

		return new BenchResult(name, callNewObject.call().getClass(), counter, totalSize, totalTime);

	}

	public <T> void benchmarkCompare(Callable<T> callNewObject, Class<T> clazz) throws Exception {

		// Compress benchmark
		final BenchResult compress1 =
				benchmark("Default Java - Compress", Duration.ofSeconds(TIME), callNewObject, BenchmarkTest::write,
						BenchmarkTest::read);
		final BenchResult compress2 =
				benchmark("Externalizor - Compress", Duration.ofSeconds(TIME), callNewObject, ExternalizerTest::write,
						bytes -> ExternalizerTest.read(bytes, clazz));

		System.out.println(compress1);
		System.out.println(compress2);
		System.out.println(compare(compress1, compress2));
		System.out.println();

		// Raw benchmark
		final BenchResult raw1 =
				benchmark("Default Java - Raw", Duration.ofSeconds(TIME), callNewObject, BenchmarkTest::writeRaw,
						BenchmarkTest::readRaw);
		final BenchResult raw2 =
				benchmark("Externalizor - Raw", Duration.ofSeconds(TIME), callNewObject, ExternalizerTest::writeRaw,
						bytes -> ExternalizerTest.readRaw(bytes, clazz));

		System.out.println(raw1);
		System.out.println(raw2);
		System.out.println(compare(raw1, raw2));
		System.out.println();
	}

	@Test
	public void benchmarkTime() throws Exception {
		benchmarkCompare(SimpleTime::new, SimpleTime.class);
	}

	@Test
	public void benchmarkLang() throws Exception {
		benchmarkCompare(SimpleLang::new, SimpleLang.class);
	}

	@Test
	public void benchmarkPrimitive() throws Exception {
		benchmarkCompare(SimplePrimitive::new, SimplePrimitive.class);
	}

	@Test
	public void benchmarkCollection() throws Exception {
		benchmarkCompare(SimpleCollection::new, SimpleCollection.class);
	}

	@Test
	public void benchmarkComplex() throws Exception {
		benchmarkCompare(ComplexExample::new, ComplexExample.class);
	}

}

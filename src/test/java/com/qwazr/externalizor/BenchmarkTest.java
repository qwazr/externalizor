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

import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class BenchmarkTest {

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

	public BenchResult benchmark(String name, Duration duration, Callable<Serializable> callNewObject,
			Function<Serializable, byte[]> serialize, Function<byte[], Serializable> deserialize) throws Exception {

		// Compute the final time
		long endTime = System.currentTimeMillis() + duration.toMillis();
		int counter = 0;
		long totalSize = 0;

		final long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() < endTime) {

			// Create a new object
			final Serializable write = callNewObject.call();

			// Serialize
			final byte[] byteArray = serialize.apply(write);
			Assert.assertNotNull(byteArray);

			//Deserialize
			final Serializable read = deserialize.apply(byteArray);
			Assert.assertNotNull(read);

			// Check equals
			Assert.assertEquals(write, read);

			totalSize += byteArray.length;
			counter++;
		}
		final long totalTime = System.currentTimeMillis() - startTime;

		return new BenchResult(name, callNewObject.call().getClass(), counter, totalSize, totalTime);

	}

	public void benchmarkCompare(Callable<Serializable> callNewObject1, Callable<Serializable> callNewObject2)
			throws Exception {

		// COmpress benchmark
		final BenchResult compress1 =
				benchmark("Compress", Duration.ofSeconds(5), callNewObject1, ExternalizerTest::write,
						ExternalizerTest::read);
		final BenchResult compress2 =
				benchmark("Compress", Duration.ofSeconds(5), callNewObject2, ExternalizerTest::write,
						ExternalizerTest::read);

		System.out.println(compress1);
		System.out.println(compress2);
		System.out.println();

		// Raw benchmark
		final BenchResult raw1 =
				benchmark("Raw ", Duration.ofSeconds(5), callNewObject1, ExternalizerTest::writeRaw,
						ExternalizerTest::readRaw);
		final BenchResult raw2 =
				benchmark("Raw", Duration.ofSeconds(5), callNewObject2, ExternalizerTest::writeRaw,
						ExternalizerTest::readRaw);


		System.out.println(raw1);
		System.out.println(raw2);
		System.out.println();
	}

	@Test
	public void ExternalTest() throws Exception {

		benchmarkCompare(SimpleTime::new, SimpleTime.External::new);
		benchmarkCompare(SimpleLang::new, SimpleLang.External::new);
		benchmarkCompare(SimplePrimitive::new, SimplePrimitive.External::new);
		benchmarkCompare(SimpleCollection::new, SimpleCollection.External::new);
		benchmarkCompare(ComplexSerial::new, ComplexExternal::new);

	}

}

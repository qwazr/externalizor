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

	public void benchmark(Duration duration, Callable<Serializable> callNewObject,
			Function<Serializable, byte[]> serialize, Function<byte[], Serializable> deserialize) throws Exception {

		// Compute the final time
		long endTime = System.currentTimeMillis() + duration.toMillis();
		int counter = 0;
		long totalSize = 0;

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

		System.out.println(
				"Counter: " + callNewObject.call().getClass() + " " + counter + " Avg. size: " + totalSize / counter);
	}

	@Test
	public void ExternalTest() throws Exception {

		/*
		benchmark(Duration.ofSeconds(5), SimpleTime::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
		benchmark(Duration.ofSeconds(5), SimpleTime.External::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));

		benchmark(Duration.ofSeconds(5), SimpleLang::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
		benchmark(Duration.ofSeconds(5), SimpleLang.External::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));

		benchmark(Duration.ofSeconds(5), SimplePrimitive::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
		benchmark(Duration.ofSeconds(5), SimplePrimitive.External::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));

		benchmark(Duration.ofSeconds(5), SimpleCollection::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
		benchmark(Duration.ofSeconds(5), SimpleCollection.External::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));

		benchmark(Duration.ofSeconds(5), ComplexSerial::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
				*/
		benchmark(Duration.ofSeconds(10), ComplexExternal::new, object -> ExternalizerTest.write(object),
				bytes -> ExternalizerTest.read(bytes));
	}
}

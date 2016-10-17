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

import java.io.*;

public class ExternalizerTest {

	private <T> byte[] write(T object) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
				oos.writeObject(object);
			}
			return bos.toByteArray();
		}
	}

	private <T> T read(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream ois = new ObjectInputStream(bis)) {
				return (T) ois.readObject();
			}
		}
	}

	@Test
	public void errorTest() throws IOException, ClassNotFoundException {

		try {
			new MissingExternalizable("Test");
			Assert.fail("The exception has not been thrown");
		} catch (ExceptionInInitializerError e) {
			Assert.assertTrue(e.getCause() instanceof ExternalizorException);
		}

	}

	@Test
	public void simpleClassTest() throws IOException, ClassNotFoundException {

		//Write
		final SerialInner write = new SerialInner();
		final byte[] byteArray = write(write);
		Assert.assertNotNull(byteArray);

		//Read
		final SerialInner read = read(byteArray);
		Assert.assertNotNull(read);

		// Check equals
		Assert.assertEquals(write, read);
	}

	@Test
	public void serialWithInnerTest() throws IOException, ClassNotFoundException {

		//Write
		final Serial write = new Serial();
		final byte[] byteArray = write(write);
		Assert.assertNotNull(byteArray);

		//Read
		final Serial read = read(byteArray);
		Assert.assertNotNull(read);

		// Check equals
		Assert.assertEquals(write, read);
		Assert.assertNotEquals(write.transientValue, read.transientValue);
	}
}

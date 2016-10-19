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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ExternalizerTest {

	private <T extends Serializable> byte[] write(T object) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			Externalizor.serialize(object, bos);
			return bos.toByteArray();
		}
	}

	private <T> T read(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			return Externalizor.deserialize(bis);
		}
	}

	@Test
	public void errorMissingExternalizableTest() throws IOException, ClassNotFoundException {
		try {
			new MissingExternalizable("Test");
			Assert.fail("The exception has not been thrown");
		} catch (ExceptionInInitializerError e) {
			Assert.assertTrue(e.getCause() instanceof ExternalizorException);
		}
	}

	@Test
	public void errorWithAbstractTest() throws IOException, ClassNotFoundException {
		try {
			new WithAbstract();
			Assert.fail("The exception has not been thrown");
		} catch (ExceptionInInitializerError e) {
			Assert.assertTrue(e.getCause() instanceof ExternalizorException);
		}
	}

	@Test
	public void errorNoEmptyConstructorTest() throws IOException, ClassNotFoundException {
		try {
			new NoEmptyConstructor("Test");
			Assert.fail("The exception has not been thrown");
		} catch (ExceptionInInitializerError e) {
			Assert.assertTrue(e.getCause() instanceof ExternalizorException);
		}
	}

	@Test
	public void errorReadBuggyTest() throws IOException, ClassNotFoundException {

		final ReadBuggyExternalizer write = new ReadBuggyExternalizer();
		final byte[] byteArray = write(write);
		Assert.assertNotNull(byteArray);

		try {
			read(byteArray);
			Assert.fail("The exception has not been thrown");
		} catch (ExternalizorException e) {
		}
	}

	private <T extends Serializable> T classTest(T write) throws IOException, ClassNotFoundException {
		//Write
		final byte[] byteArray = write(write);
		Assert.assertNotNull(byteArray);

		//Read
		final T read = read(byteArray);
		Assert.assertNotNull(read);

		// Check equals
		Assert.assertEquals(write, read);
		return read;
	}

	@Test
	public void simpleLangTest() throws IOException, ClassNotFoundException {
		classTest(new SimpleLang());
	}

	@Test
	public void simplePrimitiveTest() throws IOException, ClassNotFoundException {
		classTest(new SimplePrimitive());
	}

	@Test
	public void simpleTimeTest() throws IOException, ClassNotFoundException {
		classTest(new SimpleTime());
	}

	@Test
	public void simpleCollectionTest() throws IOException, ClassNotFoundException {
		classTest(new SimpleCollection());
	}

	@Test
	public void serialTest() throws IOException, ClassNotFoundException {
		classTest(new Serial());
	}

	@Test
	public void serialWithInnerTest() throws IOException, ClassNotFoundException {
		Serial write = new Serial();
		Serial read = classTest(write);
		Assert.assertNotEquals(write.transientValue, read.transientValue);
	}
}

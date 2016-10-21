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
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class ExternalizerTest {

	final static <T extends Serializable> byte[] write(final T object) {
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			Externalizor.serialize(object, bos);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T read(final byte[] bytes) {
		try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			return Externalizor.deserialize(bis);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
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

	private <T extends Serializable> T classTest(T write) {
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
	public void simpleLangTest() {
		classTest(new SimpleLang.External());
	}

	@Test
	public void simplePrimitiveTest() {
		classTest(new SimplePrimitive.External());
	}

	@Test
	public void simpleTimeTest() {
		classTest(new SimpleTime.External());
	}

	@Test
	public void simpleCollectionTest() {
		classTest(new SimpleCollection.External());
	}

	@Test
	public void complexWithInnerTest() {
		for (int i = 0; i < 100; i++) {
			final ComplexExternal write = new ComplexExternal();
			final ComplexExternal read = classTest(write);
			Assert.assertNotEquals(write.transientValue, read.transientValue);
		}
	}

	final static <T extends Serializable> byte[] write(final Externalizer<T, T> externalizer, final T object) {
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (final ObjectOutputStream objected = new ObjectOutputStream(bos)) {
				externalizer.writeExternal(object, objected);
			}
			bos.flush();
			return bos.toByteArray();
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T read(final Externalizer<T, T> externalizer, final byte[] bytes) {
		try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			try (final ObjectInputStream objected = new ObjectInputStream(bis)) {
				final T object = externalizer.readObject(objected);
				Assert.assertEquals(-1, objected.read());
				return object;
			}
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public <T extends Serializable> void testExternalizer(final Externalizer<T, T> externalizer, final T write) {
		final byte[] bytes = write(externalizer, write);
		final T read = read(externalizer, bytes);
		Assert.assertEquals(write, read);
	}

	@Test
	public void externalizersTest() {

		// Lang
		testExternalizer(LangExternalizer.LongExternalizer.INSTANCE, 1234L);
		testExternalizer(LangExternalizer.ShortExternalizer.INSTANCE, (short) 1234);
		testExternalizer(LangExternalizer.IntegerExternalizer.INSTANCE, 1234);
		testExternalizer(LangExternalizer.FloatExternalizer.INSTANCE, 1234F);
		testExternalizer(LangExternalizer.DoubleExternalizer.INSTANCE, 1234D);
		testExternalizer(LangExternalizer.BooleanExternalizer.INSTANCE, true);
		testExternalizer(LangExternalizer.ByteExternalizer.INSTANCE, (byte) 12);
		testExternalizer(LangExternalizer.CharExternalizer.INSTANCE, (char) 34);
		testExternalizer(LangExternalizer.StringExternalizer.INSTANCE, "1234");

		// Time
		testExternalizer(TimeExternalizer.CalendarExternalizer.INSTANCE, Calendar.getInstance());
		testExternalizer(TimeExternalizer.DateExternalizer.INSTANCE, new Date());
		testExternalizer(TimeExternalizer.DurationExternalizer.INSTANCE, Duration.ofMillis(1234));
		testExternalizer(TimeExternalizer.InstantExternalizer.INSTANCE, Instant.now());
		testExternalizer(TimeExternalizer.LocalDateExternalizer.INSTANCE, LocalDate.now());
		testExternalizer(TimeExternalizer.LocalDateTimeExternalizer.INSTANCE, LocalDateTime.now());
		testExternalizer(TimeExternalizer.LocalTimeExternalizer.INSTANCE, LocalTime.now());
		testExternalizer(TimeExternalizer.MonthDayExternalizer.INSTANCE, MonthDay.now());
		testExternalizer(TimeExternalizer.PeriodExternalizer.INSTANCE, Period.ofDays(12));
		testExternalizer(TimeExternalizer.YearExternalizer.INSTANCE, Year.now());

		// Class
		testExternalizer(new ClassExternalizer.RootExternalizer<>(ComplexExternal.class), new ComplexExternal());

	}
}

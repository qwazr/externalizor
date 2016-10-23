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
import java.util.function.Function;

public class ExternalizerTest {

	final static <T> byte[] writeCompressed(final T object) {
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			Externalizor.serialize(object, bos);
			return bos.toByteArray();
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T readCompressed(final byte[] bytes, final Class<T> clazz) {
		try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			return Externalizor.deserialize(bis, clazz);
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> byte[] writeRaw(final T object) {
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			Externalizor.serializeRaw(object, bos);
			return bos.toByteArray();
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	final static <T> T readRaw(final byte[] bytes, final Class<T> clazz) {
		try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			return Externalizor.deserializeRaw(bis, clazz);
		} catch (IOException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void abstractPropertyTest() throws IOException, ClassNotFoundException {
		classTest(new AbstractProperty("Test"));
	}

	@Test
	public void abstractCollectionTest() throws IOException, ClassNotFoundException {
		classTest(new AbstractCollection());
	}

	@Test
	public void noEmptyConstructorTest() throws IOException, ClassNotFoundException {
		classTest(new NoEmptyConstructorSerial("Test"));
	}

	private void checkError(Object object, Function<ExternalizorException, Boolean> checker) {
		try {
			classTest(object);
			Assert.fail("The exception is not thrown");
		} catch (ExternalizorException e) {
			Assert.assertTrue(checker.apply(e));
		}
	}

	@Test
	public void errorEmptyConstructorTest() throws IOException, ClassNotFoundException {
		checkError(new NoEmptyConstructor("Test"), e -> e.getMessage().contains(NoEmptyConstructor.class.getName()));
	}

	@Test
	public void errorEmptyConstructorAsFieldTest() throws IOException, ClassNotFoundException {
		checkError(new NoEmptyConstructor.AsField(), e -> e.getMessage().contains(NoEmptyConstructor.class.getName()));
	}

	private <T> T classRawTest(T write) {
		//Write
		final byte[] byteArray = writeRaw(write);
		Assert.assertNotNull(byteArray);

		//Read
		final T read = readRaw(byteArray, (Class<T>) write.getClass());
		Assert.assertNotNull(read);

		// Check equals
		Assert.assertEquals(write, read);
		return read;
	}

	private <T> T classCompressedTest(T write) {
		//Write
		final byte[] byteArray = writeCompressed(write);
		Assert.assertNotNull(byteArray);

		//Read
		final T read = readCompressed(byteArray, (Class<T>) write.getClass());
		Assert.assertNotNull(read);

		// Check equals
		Assert.assertEquals(write, read);
		return read;
	}

	private <T> T classTest(T write) {
		classRawTest(write);
		return classCompressedTest(write);
	}

	@Test
	public void simpleLangTest() {
		classTest(new SimpleLang());
	}

	@Test
	public void simplePrimitiveTest() {
		classTest(new SimplePrimitive());
	}

	@Test
	public void simpleTimeTest() {
		classTest(new SimpleTime());
	}

	@Test
	public void simpleCollectionTest() {
		classTest(new SimpleCollection());
	}

	@Test
	public void complexWithInnerTest() {
		for (int i = 0; i < 100; i++) {
			final ComplexExample write = new ComplexExample();
			final ComplexExample read = classTest(write);
			Assert.assertNotEquals(write.transientValue, read.transientValue);
		}
	}

	final static <T extends Serializable> byte[] write(final Externalizer<T, T> externalizer, final T object) {
		try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (final ObjectOutputStream objected = new ObjectOutputStream(bos)) {
				externalizer.writeExternal(object, objected);
			}
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
		testExternalizer(ClassExternalizer.of(ComplexExample.class), new ComplexExample());

	}
}

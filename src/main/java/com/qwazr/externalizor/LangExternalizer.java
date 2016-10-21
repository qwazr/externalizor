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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handle basic java.lang classes:
 * String, Long, Integer, Short, Double, Float, Character, Byte
 *
 * @param <T>
 */
interface LangExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> LangExternalizer<T, V> lang(final Class<? extends T> clazz) {
		if (String.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) StringExternalizer.INSTANCE;
		if (Long.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) LongExternalizer.INSTANCE;
		if (Integer.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) IntegerExternalizer.INSTANCE;
		if (Short.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) ShortExternalizer.INSTANCE;
		if (Double.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) DoubleExternalizer.INSTANCE;
		if (Float.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) FloatExternalizer.INSTANCE;
		if (Character.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) CharExternalizer.INSTANCE;
		if (Byte.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) ByteExternalizer.INSTANCE;
		if (Boolean.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) BooleanExternalizer.INSTANCE;
		if (Enum.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) EnumExternalizer.get((Class<? extends Enum<?>>) clazz);
		// Can't handle this class, we return null
		return null;
	}

	static <T, V> FieldExternalizer<T, V> lang(final Field field, final Class<? extends T> clazz) {
		if (String.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer<>(field,
					StringExternalizer.INSTANCE);
		if (Long.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					LongExternalizer.INSTANCE);
		if (Integer.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					IntegerExternalizer.INSTANCE);
		if (Short.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					ShortExternalizer.INSTANCE);
		if (Double.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					DoubleExternalizer.INSTANCE);
		if (Float.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					FloatExternalizer.INSTANCE);
		if (Character.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					CharExternalizer.INSTANCE);
		if (Byte.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					ByteExternalizer.INSTANCE);
		if (Boolean.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					BooleanExternalizer.INSTANCE);
		if (Enum.class.isAssignableFrom(clazz))
			return (FieldExternalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					EnumExternalizer.get((Class<? extends Enum<?>>) clazz));
		// Can't handle this class, we return null
		return null;
	}

	@Override
	default void readExternal(final T object, final ObjectInput in) throws IOException, ReflectiveOperationException {
		throw new ExternalizorException("Not available");
	}

	final class StringExternalizer implements LangExternalizer<String, String> {

		static final StringExternalizer INSTANCE = new StringExternalizer();

		@Override
		final public void writeExternal(final String object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeUTF(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public String readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readUTF() : null;
		}
	}

	final class LongExternalizer implements LangExternalizer<Long, Long> {

		static final LongExternalizer INSTANCE = new LongExternalizer();

		@Override
		final public void writeExternal(final Long object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Long readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readLong() : null;
		}
	}

	final class IntegerExternalizer implements LangExternalizer<Integer, Integer> {

		static final IntegerExternalizer INSTANCE = new IntegerExternalizer();

		@Override
		final public void writeExternal(final Integer object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeInt(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Integer readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readInt() : null;
		}
	}

	final class ShortExternalizer implements LangExternalizer<Short, Short> {

		static final ShortExternalizer INSTANCE = new ShortExternalizer();

		@Override
		final public void writeExternal(final Short object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeShort(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Short readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readShort() : null;
		}
	}

	final class DoubleExternalizer implements LangExternalizer<Double, Double> {

		static final DoubleExternalizer INSTANCE = new DoubleExternalizer();

		@Override
		final public void writeExternal(final Double object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeDouble(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Double readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readDouble() : null;
		}
	}

	final class FloatExternalizer implements LangExternalizer<Float, Float> {

		static final FloatExternalizer INSTANCE = new FloatExternalizer();

		@Override
		final public void writeExternal(final Float object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeFloat(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Float readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readFloat() : null;
		}
	}

	final class ByteExternalizer implements LangExternalizer<Byte, Byte> {

		static final ByteExternalizer INSTANCE = new ByteExternalizer();

		@Override
		final public void writeExternal(final Byte object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeByte(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Byte readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readByte() : null;
		}
	}

	final class CharExternalizer implements LangExternalizer<Character, Character> {

		static final CharExternalizer INSTANCE = new CharExternalizer();

		@Override
		final public void writeExternal(final Character object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeChar(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Character readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readChar() : null;
		}
	}

	final class BooleanExternalizer implements LangExternalizer<Boolean, Boolean> {

		static final BooleanExternalizer INSTANCE = new BooleanExternalizer();

		@Override
		final public void writeExternal(final Boolean object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeBoolean(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Boolean readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? in.readBoolean() : null;
		}
	}

	final class EnumExternalizer implements LangExternalizer<Enum<?>, Enum<?>> {

		private final static ConcurrentHashMap<Class<? extends Enum<?>>, EnumExternalizer> externalizers =
				new ConcurrentHashMap();

		private final static EnumExternalizer get(final Class<? extends Enum<?>> enumType) {
			return externalizers.computeIfAbsent(enumType, EnumExternalizer::new);
		}

		private final Class<? extends Enum> enumType;

		private EnumExternalizer(final Class<? extends Enum<?>> enumType) {
			this.enumType = enumType;
		}

		@Override
		final public void writeExternal(final Enum<?> object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeUTF(object.name());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Enum<?> readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? Enum.valueOf(enumType, in.readUTF()) : null;
		}
	}

}

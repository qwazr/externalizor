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

/**
 * Handle basic java.lang classes:
 * String, Long, Integer, Short, Double, Float, Character, Byte
 *
 * @param <T>
 */
interface LangExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> LangExternalizer<T, V> lang(final Class<? extends T> clazz) {
		if (String.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new StringExternalizer();
		if (Long.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new LongExternalizer();
		if (Integer.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new IntegerExternalizer();
		if (Short.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new ShortExternalizer();
		if (Double.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new DoubleExternalizer();
		if (Float.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FloatExternalizer();
		if (Character.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new CharExternalizer();
		if (Byte.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new ByteExternalizer();
		if (Boolean.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new BooleanExternalizer();
		// Can't handle this class, we return null
		return null;
	}

	static <T, V> LangExternalizer<T, V> lang(final Field field, final Class<? extends T> clazz) {
		if (String.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldStringExternalizer(field);
		if (Long.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldLongExternalizer(field);
		if (Integer.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldIntegerExternalizer(field);
		if (Short.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldShortExternalizer(field);
		if (Double.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldDoubleExternalizer(field);
		if (Float.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldFloatExternalizer(field);
		if (Character.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldCharExternalizer(field);
		if (Byte.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldByteExternalizer(field);
		if (Boolean.class.isAssignableFrom(clazz))
			return (LangExternalizer<T, V>) new FieldBooleanExternalizer(field);
		// Can't handle this class, we return null
		return null;
	}

	@Override
	default void readExternal(final T object, final ObjectInput in) throws IOException, ReflectiveOperationException {
		throw new ExternalizorException("Not available");
	}

	final class StringExternalizer implements LangExternalizer<String, String> {

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

	abstract class FieldLangExternalizer<T, V> extends FieldExternalizer.FieldObjectExternalizer<T, V>
			implements LangExternalizer<T, V> {

		protected FieldLangExternalizer(Field field) {
			super(field);
		}

	}

	final class FieldStringExternalizer<T> extends FieldLangExternalizer<T, String> {

		private FieldStringExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final String value, final ObjectOutput out) throws IOException {
			out.writeUTF(value);
		}

		@Override
		final public String readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readUTF() : null;
		}
	}

	final class FieldLongExternalizer<T> extends FieldLangExternalizer<T, Long> {

		private FieldLongExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Long value, final ObjectOutput out) throws IOException {
			out.writeLong(value);
		}

		@Override
		final public Long readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readLong() : null;
		}
	}

	final class FieldIntegerExternalizer<T> extends FieldLangExternalizer<T, Integer> {

		private FieldIntegerExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Integer value, final ObjectOutput out) throws IOException {
			out.writeInt(value);
		}

		@Override
		final public Integer readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readInt() : null;
		}
	}

	final class FieldShortExternalizer<T> extends FieldLangExternalizer<T, Short> {

		private FieldShortExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Short value, final ObjectOutput out) throws IOException {
			out.writeShort(value);
		}

		@Override
		final public Short readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readShort() : null;
		}
	}

	final class FieldDoubleExternalizer<T> extends FieldLangExternalizer<T, Double> {

		private FieldDoubleExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Double value, final ObjectOutput out) throws IOException {
			out.writeDouble(value);
		}

		@Override
		final public Double readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readDouble() : null;
		}
	}

	final class FieldFloatExternalizer<T> extends FieldLangExternalizer<T, Float> {

		private FieldFloatExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Float value, final ObjectOutput out) throws IOException {
			out.writeFloat(value);
		}

		@Override
		final public Float readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readFloat() : null;
		}
	}

	final class FieldCharExternalizer<T> extends FieldLangExternalizer<T, Character> {

		private FieldCharExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Character value, final ObjectOutput out) throws IOException {
			out.writeChar(value);
		}

		@Override
		final public Character readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readChar() : null;
		}
	}

	final class FieldByteExternalizer<T> extends FieldLangExternalizer<T, Byte> {

		private FieldByteExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Byte value, final ObjectOutput out) throws IOException {
			out.writeByte(value);
		}

		@Override
		final public Byte readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readByte() : null;
		}
	}

	final class FieldBooleanExternalizer<T> extends FieldLangExternalizer<T, Boolean> {

		private FieldBooleanExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected void writeValue(final Boolean value, final ObjectOutput out) throws IOException {
			out.writeBoolean(value);
		}

		@Override
		final public Boolean readObject(final ObjectInput in) throws IOException {
			return in.readBoolean() ? in.readBoolean() : null;
		}
	}
}

/**
 * Copyright 2015-2016 Emmanuel Keller / QWAZR
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

interface PrimitiveExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> PrimitiveExternalizer<T, V> primitive(final Class<T> clazz) {
		if (Integer.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new IntegerExternalizer();
		if (Short.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new ShortExternalizer();
		if (Long.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new LongExternalizer();
		if (Float.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FloatExternalizer();
		if (Double.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new DoubleExternalizer();
		if (Boolean.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new BooleanExternalizer();
		if (Byte.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new ByteExternalizer();
		if (Character.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new CharExternalizer();
		return null;
	}

	static <T, V> PrimitiveExternalizer<T, V> primitive(final Field field, final Class<T> clazz) {
		if (Integer.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldIntegerExternalizer(field);
		if (Short.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldShortExternalizer(field);
		if (Long.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldLongExternalizer(field);
		if (Float.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldFloatExternalizer(field);
		if (Double.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldDoubleExternalizer(field);
		if (Boolean.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldBooleanExternalizer(field);
		if (Byte.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldByteExternalizer(field);
		if (Character.TYPE.equals(clazz))
			return (PrimitiveExternalizer<T, V>) new FieldCharExternalizer(field);
		return null;
	}

	@Override
	default void readExternal(final T object, final ObjectInput in) throws IOException, ReflectiveOperationException {
		throw new ExternalizorException("Not implemented");
	}

	final class IntegerExternalizer implements PrimitiveExternalizer<Integer, Integer> {

		@Override
		final public void writeExternal(final Integer object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(object);
		}

		@Override
		final public Integer readObject(final ObjectInput in) throws IOException {
			return in.readInt();
		}
	}

	final class LongExternalizer implements PrimitiveExternalizer<Long, Long> {

		@Override
		final public void writeExternal(Long object, ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeLong(object);
		}

		@Override
		final public Long readObject(final ObjectInput in) throws IOException {
			return in.readLong();
		}
	}

	final class ShortExternalizer implements PrimitiveExternalizer<Short, Short> {

		@Override
		final public void writeExternal(final Short object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeShort(object);
		}

		@Override
		final public Short readObject(final ObjectInput in) throws IOException {
			return in.readShort();
		}
	}

	final class FloatExternalizer implements PrimitiveExternalizer<Float, Float> {

		@Override
		final public void writeExternal(final Float object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeFloat(object);
		}

		@Override
		final public Float readObject(final ObjectInput in) throws IOException {
			return in.readFloat();
		}
	}

	final class DoubleExternalizer implements PrimitiveExternalizer<Double, Double> {

		@Override
		final public void writeExternal(final Double object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeDouble(object);
		}

		@Override
		final public Double readObject(final ObjectInput in) throws IOException {
			return in.readDouble();
		}
	}

	final class ByteExternalizer implements PrimitiveExternalizer<Byte, Byte> {

		@Override
		final public void writeExternal(final Byte object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeByte(object);
		}

		@Override
		final public Byte readObject(final ObjectInput in) throws IOException {
			return in.readByte();
		}
	}

	final class CharExternalizer implements PrimitiveExternalizer<Character, Character> {

		@Override
		final public void writeExternal(final Character object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeChar(object);
		}

		@Override
		final public Character readObject(final ObjectInput in) throws IOException {
			return in.readChar();
		}
	}

	final class BooleanExternalizer implements PrimitiveExternalizer<Boolean, Boolean> {

		@Override
		final public void writeExternal(final Boolean object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeBoolean(object);
		}

		@Override
		final public Boolean readObject(final ObjectInput in) throws IOException {
			return in.readBoolean();
		}
	}

	abstract class FieldPrimitiveExternalizer<T, V> extends FieldExternalizer<T, V>
			implements PrimitiveExternalizer<T, V> {

		protected FieldPrimitiveExternalizer(final Field field) {
			super(field);
		}

	}

	final class FieldIntegerExternalizer<T> extends FieldPrimitiveExternalizer<T, Integer> {

		private FieldIntegerExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(field.getInt(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setInt(object, in.readInt());
		}

		@Override
		final public Integer readObject(final ObjectInput in) throws IOException {
			return in.readInt();
		}
	}

	final class FieldLongExternalizer<T> extends FieldPrimitiveExternalizer<T, Long> {

		private FieldLongExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeLong(field.getLong(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setLong(object, in.readLong());
		}

		@Override
		final public Long readObject(final ObjectInput in) throws IOException {
			return in.readLong();
		}
	}

	final class FieldShortExternalizer<T> extends FieldPrimitiveExternalizer<T, Short> {

		private FieldShortExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeShort(field.getShort(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setShort(object, in.readShort());
		}

		@Override
		final public Short readObject(final ObjectInput in) throws IOException {
			return in.readShort();
		}
	}

	final class FieldFloatExternalizer<T> extends FieldPrimitiveExternalizer<T, Float> {

		private FieldFloatExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeFloat(field.getFloat(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setFloat(object, in.readFloat());
		}

		@Override
		final public Float readObject(final ObjectInput in) throws IOException {
			return in.readFloat();
		}
	}

	final class FieldDoubleExternalizer<T> extends FieldPrimitiveExternalizer<T, Double> {

		private FieldDoubleExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeDouble(field.getDouble(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setDouble(object, in.readDouble());
		}

		@Override
		final public Double readObject(final ObjectInput in) throws IOException {
			return in.readDouble();
		}
	}

	final class FieldByteExternalizer<T> extends FieldPrimitiveExternalizer<T, Byte> {

		private FieldByteExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeByte(field.getByte(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setByte(object, in.readByte());
		}

		@Override
		final public Byte readObject(final ObjectInput in) throws IOException {
			return in.readByte();
		}
	}

	final class FieldCharExternalizer<T> extends FieldPrimitiveExternalizer<T, Character> {

		private FieldCharExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeChar(field.getChar(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setChar(object, in.readChar());
		}

		@Override
		final public Character readObject(final ObjectInput in) throws IOException {
			return in.readChar();
		}
	}

	final class FieldBooleanExternalizer<T> extends FieldPrimitiveExternalizer<T, Boolean> {

		private FieldBooleanExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeBoolean(field.getBoolean(object));
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.setBoolean(object, in.readBoolean());
		}

		@Override
		final public Boolean readObject(final ObjectInput in) throws IOException {
			return in.readBoolean();
		}
	}

}

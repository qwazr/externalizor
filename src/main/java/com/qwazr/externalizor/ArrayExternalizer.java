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

interface ArrayExternalizer<T, V> extends Externalizer<T, V> {

	static FieldExternalizer array(final Field field, final Class<?> clazz) {
		final Class<?> componentType = clazz.getComponentType();
		if (componentType.isPrimitive()) {
			if (Integer.TYPE.equals(componentType))
				return new FieldArrayIntegerExternalizer(field);
			if (Short.TYPE.equals(componentType))
				return new FieldArrayShortExternalizer(field);
			if (Long.TYPE.equals(componentType))
				return new FieldArrayLongExternalizer(field);
			if (Float.TYPE.equals(componentType))
				return new FieldArrayFloatExternalizer(field);
			if (Double.TYPE.equals(componentType))
				return new FieldArrayDoubleExternalizer(field);
			if (Boolean.TYPE.equals(componentType))
				return new FieldArrayBooleanExternalizer(field);
			if (Byte.TYPE.equals(componentType))
				return new FieldArrayByteExternalizer(field);
			if (Character.TYPE.equals(componentType))
				return new FieldArrayCharExternalizer(field);
		}
		Externalizer externalizer = Externalizer.of(componentType);
		if (externalizer != null) {
			if (String.class.isAssignableFrom(componentType))
				return new FieldArrayLangStringExternalizer(field, externalizer);
			if (Double.class.isAssignableFrom(componentType))
				return new FieldArrayLangDoubleExternalizer(field, externalizer);
			if (Float.class.isAssignableFrom(componentType))
				return new FieldArrayLangFloatExternalizer(field, externalizer);
			if (Long.class.isAssignableFrom(componentType))
				return new FieldArrayLangLongExternalizer(field, externalizer);
			if (Short.class.isAssignableFrom(componentType))
				return new FieldArrayLangShortExternalizer(field, externalizer);
			if (Integer.class.isAssignableFrom(componentType))
				return new FieldArrayLangIntExternalizer(field, externalizer);
			if (Boolean.class.isAssignableFrom(componentType))
				return new FieldArrayLangBooleanExternalizer(field, externalizer);
			if (Character.class.isAssignableFrom(componentType))
				return new FieldArrayLangCharExternalizer(field, externalizer);
			if (Byte.class.isAssignableFrom(componentType))
				return new FieldArrayLangByteExternalizer(field, externalizer);
		}
		return null;
	}

	V readArray(final int size, final ObjectInput in) throws IOException, ReflectiveOperationException;

	abstract class FieldArrayExternalizer<T, V> extends FieldExternalizer.FieldObjectExternalizer<T, V>
			implements ArrayExternalizer<T, V> {

		protected FieldArrayExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public V readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			return readArray(in.readInt(), in);
		}

	}

	final class FieldArrayIntegerExternalizer<T> extends FieldArrayExternalizer<T, int[]> {

		private FieldArrayIntegerExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public int[] readArray(final int size, final ObjectInput in) throws IOException {
			final int[] array = new int[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readInt();
			return array;
		}

		@Override
		protected void writeValue(final int[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (int item : value)
				out.writeInt(item);
		}
	}

	final class FieldArrayLongExternalizer<T> extends FieldArrayExternalizer<T, long[]> {

		private FieldArrayLongExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public long[] readArray(final int size, final ObjectInput in) throws IOException {
			final long[] array = new long[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readLong();
			return array;
		}

		@Override
		protected void writeValue(final long[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (long item : value)
				out.writeLong(item);
		}
	}

	final class FieldArrayShortExternalizer<T> extends FieldArrayExternalizer<T, short[]> {

		private FieldArrayShortExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public short[] readArray(final int size, final ObjectInput in) throws IOException {
			final short[] array = new short[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readShort();
			return array;
		}

		@Override
		protected void writeValue(final short[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (short item : value)
				out.writeShort(item);
		}
	}

	final class FieldArrayDoubleExternalizer<T> extends FieldArrayExternalizer<T, double[]> {

		private FieldArrayDoubleExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public double[] readArray(final int size, final ObjectInput in) throws IOException {
			final double[] array = new double[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readDouble();
			return array;
		}

		@Override
		protected void writeValue(final double[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (double item : value)
				out.writeDouble(item);
		}
	}

	final class FieldArrayFloatExternalizer<T> extends FieldArrayExternalizer<T, float[]> {

		private FieldArrayFloatExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public float[] readArray(final int size, final ObjectInput in) throws IOException {
			final float[] array = new float[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readFloat();
			return array;
		}

		@Override
		protected void writeValue(final float[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (float item : value)
				out.writeFloat(item);
		}
	}

	final class FieldArrayByteExternalizer<T> extends FieldArrayExternalizer<T, byte[]> {

		private FieldArrayByteExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public byte[] readArray(final int size, final ObjectInput in) throws IOException {
			final byte[] array = new byte[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readByte();
			return array;
		}

		@Override
		protected void writeValue(final byte[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (byte item : value)
				out.writeByte(item);
		}
	}

	final class FieldArrayCharExternalizer<T> extends FieldArrayExternalizer<T, char[]> {

		private FieldArrayCharExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public char[] readArray(final int size, final ObjectInput in) throws IOException {
			final char[] array = new char[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readChar();
			return array;
		}

		@Override
		protected void writeValue(final char[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (char item : value)
				out.writeChar(item);
		}
	}

	final class FieldArrayBooleanExternalizer<T> extends FieldArrayExternalizer<T, boolean[]> {

		private FieldArrayBooleanExternalizer(final Field field) {
			super(field);
		}

		@Override
		final public boolean[] readArray(final int size, final ObjectInput in) throws IOException {
			final boolean[] array = new boolean[size];
			for (int i = 0; i < array.length; i++)
				array[i] = in.readBoolean();
			return array;
		}

		@Override
		protected void writeValue(final boolean[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (boolean item : value)
				out.writeBoolean(item);
		}
	}

	abstract class FieldArrayObjectExternalizer<T, V> extends FieldArrayExternalizer<T, V>
			implements ArrayExternalizer<T, V> {

		protected final Externalizer componentExternalizer;

		protected FieldArrayObjectExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field);
			this.componentExternalizer = componentExternalizer;
		}

	}

	final class FieldArrayLangStringExternalizer<T> extends FieldArrayObjectExternalizer<T, String[]> {

		FieldArrayLangStringExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public String[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final String[] array = new String[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (String) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final String[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (String item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangDoubleExternalizer<T> extends FieldArrayObjectExternalizer<T, Double[]> {

		FieldArrayLangDoubleExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Double[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Double[] array = new Double[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Double) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Double[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Double item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangFloatExternalizer<T> extends FieldArrayObjectExternalizer<T, Float[]> {

		FieldArrayLangFloatExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Float[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Float[] array = new Float[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Float) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Float[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Float item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangLongExternalizer<T> extends FieldArrayObjectExternalizer<T, Long[]> {

		FieldArrayLangLongExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Long[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Long[] array = new Long[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Long) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Long[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Long item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangIntExternalizer<T> extends FieldArrayObjectExternalizer<T, Integer[]> {

		FieldArrayLangIntExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Integer[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Integer[] array = new Integer[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Integer) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Integer[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Integer item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangShortExternalizer<T> extends FieldArrayObjectExternalizer<T, Short[]> {

		FieldArrayLangShortExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Short[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Short[] array = new Short[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Short) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Short[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Short item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangCharExternalizer<T> extends FieldArrayObjectExternalizer<T, Character[]> {

		FieldArrayLangCharExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Character[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Character[] array = new Character[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Character) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Character[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Character item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangByteExternalizer<T> extends FieldArrayObjectExternalizer<T, Byte[]> {

		FieldArrayLangByteExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Byte[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Byte[] array = new Byte[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Byte) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Byte[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Byte item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}

	final class FieldArrayLangBooleanExternalizer<T> extends FieldArrayObjectExternalizer<T, Boolean[]> {

		FieldArrayLangBooleanExternalizer(final Field field, final Externalizer componentExternalizer) {
			super(field, componentExternalizer);
		}

		@Override
		final public Boolean[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Boolean[] array = new Boolean[size];
			for (int i = 0; i < array.length; i++)
				array[i] = (Boolean) componentExternalizer.readObject(in);
			return array;
		}

		@Override
		protected void writeValue(final Boolean[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Boolean item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}
}

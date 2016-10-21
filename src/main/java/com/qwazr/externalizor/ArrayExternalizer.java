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

import org.xerial.snappy.Snappy;

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
		final Externalizer externalizer = Externalizer.of(componentType);
		if (externalizer != null)
			return new FieldArrayLangObjectExternalizer(field, componentType, externalizer);
		return null;
	}

	abstract class FieldArrayExternalizer<T, V> extends FieldExternalizer.FieldObjectExternalizer<T, V>
			implements ArrayExternalizer<T, V> {

		protected FieldArrayExternalizer(final Field field) {
			super(field);
		}

		protected abstract V readArray(int size, ObjectInput in) throws IOException, ReflectiveOperationException;

		@Override
		final public V readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			return readArray(in.readInt(), in);
		}

	}

	abstract class FieldArrayPrimitiveExternalizer<T, V> extends FieldArrayExternalizer<T, V> {

		protected FieldArrayPrimitiveExternalizer(final Field field) {
			super(field);
		}

		final protected void writeByteArray(final byte[] bytes, final ObjectOutput out) throws IOException {
			out.writeInt(bytes.length);
			if (bytes.length > 0)
				out.write(bytes);
		}

		final protected V readArray(final int size, final ObjectInput in) throws IOException {
			final byte[] bytes = new byte[size];
			in.read(bytes);
			return readByteArray(bytes);
		}

		protected abstract V readByteArray(final byte[] bytes) throws IOException;
	}

	final class FieldArrayIntegerExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, int[]> {

		private FieldArrayIntegerExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected int[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressIntArray(bytes);
		}

		@Override
		final protected void writeValue(final int[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayLongExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, long[]> {

		private FieldArrayLongExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected long[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressLongArray(bytes);
		}

		@Override
		protected void writeValue(final long[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayShortExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, short[]> {

		private FieldArrayShortExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected short[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressShortArray(bytes);
		}

		@Override
		final protected void writeValue(final short[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayDoubleExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, double[]> {

		private FieldArrayDoubleExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected double[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressDoubleArray(bytes);
		}

		@Override
		final protected void writeValue(final double[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayFloatExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, float[]> {

		private FieldArrayFloatExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected float[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressFloatArray(bytes);
		}

		@Override
		final protected void writeValue(final float[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayByteExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, byte[]> {

		private FieldArrayByteExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected byte[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompress(bytes);
		}

		@Override
		final protected void writeValue(final byte[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayCharExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, char[]> {

		private FieldArrayCharExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected char[] readByteArray(final byte[] bytes) throws IOException {
			return Snappy.uncompressCharArray(bytes);
		}

		@Override
		final protected void writeValue(final char[] value, final ObjectOutput out) throws IOException {
			writeByteArray(Snappy.compress(value), out);
		}
	}

	final class FieldArrayBooleanExternalizer<T> extends FieldArrayPrimitiveExternalizer<T, boolean[]> {

		private FieldArrayBooleanExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected boolean[] readByteArray(final byte[] bytes) throws IOException {
			final byte[] booleanBytes = Snappy.uncompress(bytes);
			final boolean[] booleans = new boolean[booleanBytes.length];
			for (int i = 0; i < booleanBytes.length; i++)
				booleans[i] = booleanBytes[i] == 1;
			return booleans;
		}

		@Override
		final protected void writeValue(final boolean[] value, final ObjectOutput out) throws IOException {
			byte[] bytes = new byte[value.length];
			for (int i = 0; i < value.length; i++)
				bytes[i] = (byte) (value[i] ? 1 : 0);
			writeByteArray(Snappy.compress(bytes), out);
		}
	}

	final class FieldArrayLangObjectExternalizer<T> extends FieldArrayExternalizer<T, Object[]>
			implements ArrayExternalizer<T, Object[]> {

		private final Externalizer componentExternalizer;
		private final Class<?> componentType;

		FieldArrayLangObjectExternalizer(final Field field, final Class<?> componentType,
				final Externalizer componentExternalizer) {
			super(field);
			this.componentExternalizer = componentExternalizer;
			this.componentType = componentType;
		}

		@Override
		final public Object[] readArray(final int size, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			final Object[] array = (Object[]) java.lang.reflect.Array.newInstance(componentType, size);
			for (int i = 0; i < array.length; i++)
				array[i] = componentExternalizer.readObject(in);
			return array;
		}

		@Override
		final protected void writeValue(final Object[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (Object item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}
}

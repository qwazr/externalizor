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

import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;
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

	static void writeBytes(final byte[] bytes, final ObjectOutput out) throws IOException {
		out.writeInt(bytes.length);
		out.write(bytes, 0, bytes.length);
	}

	static byte[] readBytes(final ObjectInput in) throws IOException {
		final byte[] bytes = new byte[in.readInt()];
		in.read(bytes, 0, bytes.length);
		return bytes;
	}

	abstract class FieldArraySnappyExternalizer<T, V> extends FieldExternalizer.FieldObjectExternalizer<T, V> {

		protected FieldArraySnappyExternalizer(final Field field) {
			super(field);
		}

		protected abstract V uncompress(final byte[] bytes) throws IOException;

		protected abstract byte[] compress(final V value) throws IOException;

		final protected void writeValue(final V value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			writeBytes(compress(value), out);
		}

		@Override
		final public V readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			return uncompress(readBytes(in));
		}

	}

	final class FieldArrayIntegerExternalizer<T> extends FieldArraySnappyExternalizer<T, int[]> {

		private FieldArrayIntegerExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected int[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressIntArray(bytes);
		}

		@Override
		final protected byte[] compress(final int[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayLongExternalizer<T> extends FieldArraySnappyExternalizer<T, long[]> {

		private FieldArrayLongExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected long[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressLongArray(bytes);
		}

		@Override
		final protected byte[] compress(final long[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayShortExternalizer<T> extends FieldArraySnappyExternalizer<T, short[]> {

		private FieldArrayShortExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected short[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressShortArray(bytes);
		}

		@Override
		final protected byte[] compress(final short[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayDoubleExternalizer<T> extends FieldArraySnappyExternalizer<T, double[]> {

		private FieldArrayDoubleExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected double[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressDoubleArray(bytes);
		}

		@Override
		final protected byte[] compress(final double[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayFloatExternalizer<T> extends FieldArraySnappyExternalizer<T, float[]> {

		private FieldArrayFloatExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected float[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressFloatArray(bytes);
		}

		@Override
		final protected byte[] compress(final float[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayByteExternalizer<T> extends FieldArraySnappyExternalizer<T, byte[]> {

		private FieldArrayByteExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected byte[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompress(bytes);
		}

		@Override
		final protected byte[] compress(final byte[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayCharExternalizer<T> extends FieldArraySnappyExternalizer<T, char[]> {

		private FieldArrayCharExternalizer(final Field field) {
			super(field);
		}

		@Override
		final protected char[] uncompress(final byte[] bytes) throws IOException {
			return Snappy.uncompressCharArray(bytes);
		}

		@Override
		final protected byte[] compress(final char[] value) throws IOException {
			return Snappy.compress(value);
		}
	}

	final class FieldArrayBooleanExternalizer<T> extends FieldExternalizer.FieldObjectExternalizer<T, boolean[]> {

		private FieldArrayBooleanExternalizer(final Field field) {
			super(field);
		}

		@Override
		public boolean[] readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final boolean[] array = new boolean[in.readInt()];
			final RoaringBitmap bitmap = new RoaringBitmap();
			bitmap.readExternal(in);
			bitmap.forEach((IntConsumer) i -> array[i] = true);
			return array;
		}


		@Override
		final protected void writeValue(final boolean[] value, final ObjectOutput out) throws IOException {
			out.writeInt(value.length);
			final RoaringBitmap bitmap = new RoaringBitmap();
			for (int i = 0; i < value.length; i++)
				if (value[i])
					bitmap.add(i);
			bitmap.writeExternal(out);
		}
	}

	final class FieldArrayLangObjectExternalizer<T> extends FieldExternalizer.FieldObjectExternalizer<T, Object[]>
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
		final public Object[] readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final Object[] array = (Object[]) java.lang.reflect.Array.newInstance(componentType, in.readInt());
			for (int i = 0; i < array.length; i++)
				array[i] = componentExternalizer.readObject(in);
			return array;
		}

		@Override
		final protected void writeValue(final Object[] value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(value.length);
			for (final Object item : value)
				componentExternalizer.writeExternal(item, out);
		}
	}
}

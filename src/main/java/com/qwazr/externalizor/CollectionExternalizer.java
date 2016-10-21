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

import org.roaringbitmap.RoaringBitmap;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

interface CollectionExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> CollectionExternalizer<T, V> collection(final Field field, final Class<? extends T> clazz) {
		if (Map.class.isAssignableFrom(clazz))
			return (CollectionExternalizer<T, V>) new FieldMapExternalizer(field, clazz);
		if (Collection.class.isAssignableFrom(clazz)) {
			final Class<?> genericClass = FieldExternalizer.getGenericClass(field, 0);
			if (Long.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionLongExternalizer(field, clazz);
			if (Integer.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionIntegerExternalizer(field, clazz);
			if (Short.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionShortExternalizer(field, clazz);
			if (Double.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionDoubleExternalizer(field, clazz);
			if (Float.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionFloatExternalizer(field, clazz);
			if (Character.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionCharacterExternalizer(field, clazz);
			if (Byte.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionByteExternalizer(field, clazz);
			if (Boolean.class.isAssignableFrom(genericClass))
				return (CollectionExternalizer<T, V>) new FieldCollectionBooleanExternalizer(field, clazz);
			return (CollectionExternalizer<T, V>) new FieldCollectionExternalizer(field, clazz);
		}
		// Can't handle this class, we return null
		return null;
	}

	abstract class FieldCollectionSnappyExternalizer<T, V>
			extends FieldExternalizer.FieldConstructorExternalizer<T, Collection<V>>
			implements CollectionExternalizer<T, Collection<V>> {

		protected FieldCollectionSnappyExternalizer(final Field field, final Class<? extends Collection<V>> clazz) {
			super(field, clazz);
		}

		protected interface NullableArray<V> {

			void set(final int i, final V value);

			byte[] compress() throws IOException;
		}

		protected abstract NullableArray getNullableArray(final int size);

		@Override
		final protected void writeValue(final Collection<V> collection, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			final NullableArray array = getNullableArray(collection.size());
			final RoaringBitmap nullBitmap = new RoaringBitmap();
			int i = 0;
			for (final V value : collection) {
				if (value == null)
					nullBitmap.add(i);
				else
					array.set(i, value);
				i++;
			}
			nullBitmap.writeExternal(out);
			ArrayExternalizer.writeBytes(array.compress(), out);
			out.flush();
		}

		abstract protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				Collection<V> collection) throws IOException;

		@Override
		final public Collection<V> readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final RoaringBitmap nullBitmap = new RoaringBitmap();
			nullBitmap.readExternal(in);
			final Collection<V> collection = constructor.newInstance();
			fillCollection(in, nullBitmap, collection);
			return collection;
		}
	}

	final class FieldCollectionLongExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Long> {

		protected FieldCollectionLongExternalizer(final Field field, final Class<? extends Collection<Long>> clazz) {
			super(field, clazz);
		}

		final private class LongNullableArray implements NullableArray<Long> {

			final long[] array;

			private LongNullableArray(final int size) {
				array = new long[size];
			}

			@Override
			final public void set(final int i, final Long value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(int size) {
			return new LongNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Long> collection) throws IOException {
			final long[] array = Snappy.uncompressLongArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final long value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionIntegerExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Integer> {

		protected FieldCollectionIntegerExternalizer(final Field field,
				final Class<? extends Collection<Integer>> clazz) {
			super(field, clazz);
		}

		final private class IntegerNullableArray implements NullableArray<Integer> {

			final int[] array;

			private IntegerNullableArray(final int size) {
				array = new int[size];
			}

			@Override
			final public void set(final int i, final Integer value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(int size) {
			return new IntegerNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Integer> collection) throws IOException {
			final int[] array = Snappy.uncompressIntArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final int value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionShortExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Short> {

		protected FieldCollectionShortExternalizer(final Field field, final Class<? extends Collection<Short>> clazz) {
			super(field, clazz);
		}

		final private class ShortNullableArray implements NullableArray<Short> {

			final short[] array;

			private ShortNullableArray(final int size) {
				array = new short[size];
			}

			@Override
			final public void set(final int i, final Short value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(final int size) {
			return new ShortNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Short> collection) throws IOException {
			final short[] array = Snappy.uncompressShortArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final short value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionDoubleExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Double> {

		protected FieldCollectionDoubleExternalizer(final Field field,
				final Class<? extends Collection<Double>> clazz) {
			super(field, clazz);
		}

		final private class DoubleNullableArray implements NullableArray<Double> {

			final double[] array;

			private DoubleNullableArray(final int size) {
				array = new double[size];
			}

			@Override
			final public void set(final int i, final Double value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(final int size) {
			return new DoubleNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Double> collection) throws IOException {
			final double[] array = Snappy.uncompressDoubleArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final double value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionFloatExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Float> {

		protected FieldCollectionFloatExternalizer(final Field field, final Class<? extends Collection<Float>> clazz) {
			super(field, clazz);
		}

		final private class FloatNullableArray implements NullableArray<Float> {

			final float[] array;

			private FloatNullableArray(final int size) {
				array = new float[size];
			}

			@Override
			final public void set(final int i, final Float value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(final int size) {
			return new FloatNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Float> collection) throws IOException {
			final float[] array = Snappy.uncompressFloatArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final float value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionCharacterExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Character> {

		protected FieldCollectionCharacterExternalizer(final Field field,
				final Class<? extends Collection<Character>> clazz) {
			super(field, clazz);
		}

		final private class CharacterNullableArray implements NullableArray<Character> {

			final char[] array;

			private CharacterNullableArray(final int size) {
				array = new char[size];
			}

			@Override
			final public void set(final int i, final Character value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(final int size) {
			return new CharacterNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Character> collection) throws IOException {
			final char[] array = Snappy.uncompressCharArray(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final char value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionByteExternalizer<T> extends FieldCollectionSnappyExternalizer<T, Byte> {

		protected FieldCollectionByteExternalizer(final Field field, final Class<? extends Collection<Byte>> clazz) {
			super(field, clazz);
		}

		final private class ByteNullableArray implements NullableArray<Byte> {

			final byte[] array;

			private ByteNullableArray(final int size) {
				array = new byte[size];
			}

			@Override
			final public void set(final int i, final Byte value) {
				array[i] = value;
			}

			@Override
			final public byte[] compress() throws IOException {
				return Snappy.compress(array);
			}
		}

		@Override
		protected NullableArray getNullableArray(final int size) {
			return new ByteNullableArray(size);
		}

		final protected void fillCollection(final ObjectInput in, final RoaringBitmap nullBitmap,
				final Collection<Byte> collection) throws IOException {
			final byte[] array = Snappy.uncompress(ArrayExternalizer.readBytes(in));
			int i = 0;
			for (final byte value : array)
				collection.add(nullBitmap.contains(i++) ? null : value);
		}
	}

	final class FieldCollectionBooleanExternalizer<T>
			extends FieldExternalizer.FieldConstructorExternalizer<T, Collection<Boolean>>
			implements CollectionExternalizer<T, Collection<Boolean>> {

		protected FieldCollectionBooleanExternalizer(final Field field,
				final Class<? extends Collection<Boolean>> clazz) {
			super(field, clazz);
		}

		@Override
		final protected void writeValue(final Collection<Boolean> collection, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			final RoaringBitmap nullBitmap = new RoaringBitmap();
			final RoaringBitmap booleanBitmap = new RoaringBitmap();
			int i = 0;
			for (final Boolean value : collection) {
				if (value != null) {
					if (value)
						booleanBitmap.add(i);
				} else
					nullBitmap.add(i);
				i++;
			}
			out.writeInt(collection.size());
			nullBitmap.writeExternal(out);
			booleanBitmap.writeExternal(out);
		}

		@Override
		final public Collection<Boolean> readObject(final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final Collection<Boolean> collection = constructor.newInstance();
			final boolean[] array = new boolean[in.readInt()];
			final RoaringBitmap nullBitmap = new RoaringBitmap();
			final RoaringBitmap booleanBitmap = new RoaringBitmap();
			nullBitmap.readExternal(in);
			booleanBitmap.readExternal(in);
			for (int i = 0; i < array.length; i++) {
				if (nullBitmap.contains(i))
					collection.add(null);
				else
					collection.add(booleanBitmap.contains(i));
			}
			return collection;
		}
	}

	final class FieldCollectionExternalizer<T> extends FieldExternalizer.FieldConstructorExternalizer<T, Collection<?>>
			implements CollectionExternalizer<T, Collection<?>> {

		protected final Externalizer<Object, ?> componentExternalizer;

		protected FieldCollectionExternalizer(final Field field, Class<? extends Collection<?>> clazz) {
			super(field, clazz);
			componentExternalizer = getGeneric(0);
		}

		@Override
		final protected void writeValue(final Collection<?> collection, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(collection.size());
			for (Object o : collection)
				componentExternalizer.writeExternal(o, out);
		}

		@Override
		final public Collection readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final Collection collection = constructor.newInstance();
			int size = in.readInt();
			while (size-- > 0)
				collection.add(componentExternalizer.readObject(in));
			return collection;
		}
	}

	final class FieldMapExternalizer<T> extends FieldExternalizer.FieldConstructorExternalizer<T, Map<?, ?>>
			implements CollectionExternalizer<T, Map<?, ?>> {

		private final Externalizer<Object, ?> keyExternalizer;
		private final Externalizer<Object, ?> valueExternalizer;

		private FieldMapExternalizer(final Field field, Class<? extends Map<?, ?>> clazz) {
			super(field, clazz);
			keyExternalizer = getGeneric(0);
			valueExternalizer = getGeneric(1);
		}

		@Override
		final protected void writeValue(final Map<?, ?> map, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			out.writeInt(map.size());
			for (Map.Entry entry : map.entrySet()) {
				keyExternalizer.writeExternal(entry.getKey(), out);
				valueExternalizer.writeExternal(entry.getValue(), out);
			}
		}

		@Override
		final public Map<?, ?> readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final Map map = constructor.newInstance();
			int size = in.readInt();
			while (size-- > 0)
				map.put(keyExternalizer.readObject(in), valueExternalizer.readObject(in));
			return map;
		}
	}

}

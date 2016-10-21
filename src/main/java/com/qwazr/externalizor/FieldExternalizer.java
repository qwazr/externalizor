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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

abstract class FieldExternalizer<T, V> implements Externalizer<T, V> {

	protected final Field field;

	protected FieldExternalizer(final Field field) {
		this.field = field;
	}

	static abstract class FieldObjectExternalizer<T, V> extends FieldExternalizer<T, V> {

		protected FieldObjectExternalizer(final Field field) {
			super(field);
		}

		protected abstract void writeValue(final V value, final ObjectOutput out)
				throws IOException, ReflectiveOperationException;

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.set(object, readObject(in));
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			final V value = (V) field.get(object);
			if (value == null) {
				out.writeBoolean(false);
				return;
			}
			out.writeBoolean(true);
			writeValue(value, out);
		}
	}

	static class FieldParentExternalizer<T, V> extends FieldExternalizer<T, V> {

		private final Externalizer<V, V> externalizer;

		protected FieldParentExternalizer(final Field field, final Externalizer<V, V> externalizer) {
			super(field);
			this.externalizer = externalizer;
		}

		@Override
		final public V readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			return externalizer.readObject(in);
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			field.set(object, readObject(in));
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			externalizer.writeExternal((V) field.get(object), out);
		}
	}

	static abstract class FieldConstructorExternalizer<T, C> extends FieldObjectExternalizer<T, C> {

		protected final Constructor<? extends C> constructor;

		protected FieldConstructorExternalizer(final Field field, final Class<? extends C> clazz) {
			super(field);
			try {
				constructor = clazz.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new ExternalizorException("Not empty public constructor for the type " + clazz);
			}
		}

		final protected Externalizer<Object, ?> getGeneric(final int pos) {
			final ParameterizedType paramTypes = (ParameterizedType) field.getGenericType();
			return Externalizer.of((Class<?>) paramTypes.getActualTypeArguments()[pos]);
		}
	}

}

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
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

interface ClassExternalizer<T> extends Externalizer<T, T> {

	static <T> ClassExternalizer<T> of(final Class<T> clazz) {
		try {
			final Constructor<T> constructor = clazz.getConstructor();
			final Collection<Externalizer> externalizers = new ArrayList<>();
			detectFields(clazz, externalizers);
			if (externalizers.size() > 0)
				return new RootExternalizer(constructor, externalizers);
		} catch (NoSuchMethodException e) {
		}
		if (Serializable.class.isAssignableFrom(clazz))
			return new SerializableExternalizer<>(clazz);
		throw new ExternalizorException("Externalizer does not support serialization of: " + clazz);
	}

	static void detectFields(final Class<?> clazz, final Collection<Externalizer> externalizers) {
		if (clazz == null)
			return;
		final Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			final Class<?> cl = field.getType();
			final int modifier = field.getModifiers();
			if (Modifier.isStatic(modifier) || Modifier.isTransient(modifier))
				continue;
			field.setAccessible(true);
			final Externalizer fieldExt = Externalizer.of(field, cl);
			externalizers.add(fieldExt);
		}
		detectFields(clazz.getSuperclass(), externalizers);
	}

	final class RootExternalizer<T> implements ClassExternalizer<T> {

		private final Constructor<T> constructor;
		private final Collection<Externalizer> externalizers;

		private RootExternalizer(final Constructor<T> constructor, final Collection<Externalizer> externalizers) {
			this.constructor = constructor;
			this.externalizers = externalizers;
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			for (final Externalizer externalizer : externalizers)
				externalizer.writeExternal(object, out);
		}

		@Override
		final public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			for (final Externalizer externalizer : externalizers)
				externalizer.readExternal(object, in);
		}

		@Override
		final public T readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			final T object = constructor.newInstance();
			readExternal(object, in);
			return object;
		}
	}

	final class SerializableExternalizer<T> implements ClassExternalizer<T> {

		private final Class<T> clazz;

		private SerializableExternalizer(final Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeObject(object);
			} else
				out.writeBoolean(false);
		}

		@Override
		public void readExternal(T object, ObjectInput in) throws IOException, ReflectiveOperationException {
			throw new ExternalizorException("Cannot read external from " + clazz);
		}

		@Override
		public T readObject(ObjectInput in) throws IOException, ReflectiveOperationException {
			return in.readBoolean() ? (T) in.readObject() : null;
		}
	}
}
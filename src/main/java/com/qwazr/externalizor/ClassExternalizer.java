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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

interface ClassExternalizer<T extends Externalizable> extends Externalizer<T, T> {

	final class RootExternalizer<T extends Externalizable> implements ClassExternalizer<T> {

		private final Constructor<T> constructor;
		private final Collection<Externalizer> externalizers;

		RootExternalizer(final Class<T> clazz) {
			try {
				constructor = clazz.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new ExternalizorException("The clazz does not have a public empty constructor: " + clazz, e);
			}
			final Field[] fields = clazz.getDeclaredFields();
			externalizers = new ArrayList<>(fields.length);
			detectFields(clazz);
		}

		private void detectFields(final Class<?> clazz) {
			if (clazz == null)
				return;
			final Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				final Class<?> cl = field.getType();
				final int modifier = field.getModifiers();
				if (Modifier.isStatic(modifier) || Modifier.isTransient(modifier))
					continue;
				field.setAccessible(true);
				externalizers.add(Externalizer.of(field, cl));
			}
			detectFields(clazz.getSuperclass());
		}

		@Override
		final public void writeExternal(final Externalizable object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			for (final Externalizer externalizer : externalizers)
				externalizer.writeExternal(object, out);
		}

		@Override
		final public void readExternal(final Externalizable object, final ObjectInput in)
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

	final class AbleExternalizer<T extends Externalizable> implements ClassExternalizer<T> {

		private final Class<T> clazz;

		AbleExternalizer(final Class<T> clazz) {
			this.clazz = clazz;
			System.out.println("CLAZZ: " + clazz);
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			if (object == null) {
				out.writeBoolean(false);
				return;
			}
			out.writeBoolean(true);
			object.writeExternal(out);
		}

		@Override
		public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			if (in.readBoolean())
				object.readExternal(in);
		}

		@Override
		final public T readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final T object = clazz.newInstance();
			object.readExternal(in);
			return object;
		}
	}
}
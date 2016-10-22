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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

interface ClassExternalizer<T> extends Externalizer<T, T> {

	final class RootExternalizer<T> implements ClassExternalizer<T> {

		private final Constructor<T> constructor;
		private final Collection<Externalizer> externalizers;

		RootExternalizer(final Class<T> clazz) {
			try {
				constructor = clazz.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new ExternalizorException("The clazz does not have a public empty constructor: " + clazz, e);
			}
			externalizers = new ArrayList<>();
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
				final Externalizer fieldExt = Externalizer.of(field, cl);
				externalizers.add(fieldExt);
			}
			detectFields(clazz.getSuperclass());
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			for (final Externalizer externalizer : externalizers)
				externalizer.writeExternal(object, out);
			out.flush();
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

	final class AbleExternalizer<T> implements ClassExternalizer<T> {

		private final Class<T> clazz;

		AbleExternalizer(final Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		final public void writeExternal(final T object, final ObjectOutput out)
				throws IOException, ReflectiveOperationException {
			if (object == null) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				Externalizor.of(clazz).writeExternal(object, out);
			}
			out.flush();
		}

		@Override
		public void readExternal(final T object, final ObjectInput in)
				throws IOException, ReflectiveOperationException {
			if (in.readBoolean())
				Externalizor.of(clazz).readExternal(object, in);
		}

		@Override
		final public T readObject(final ObjectInput in) throws IOException, ReflectiveOperationException {
			if (!in.readBoolean())
				return null;
			final T object = clazz.newInstance();
			Externalizor.of(clazz).readExternal(object, in);
			return object;
		}
	}
}
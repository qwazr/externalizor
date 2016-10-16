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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

public final class ClassExternalizer<T> implements Externalizer<T, T> {

	private final Class<T> clazz;

	private final Collection<Externalizer> externalizers;

	ClassExternalizer(final Class<T> clazz) {
		this.clazz = clazz;
		final Field[] fields = clazz.getDeclaredFields();
		externalizers = new ArrayList<>(fields.length);
		for (Field field : fields) {
			final Class<?> cl = field.getType();
			final int modifier = field.getModifiers();
			if (Modifier.isStatic(modifier) || Modifier.isTransient(modifier))
				continue;
			field.setAccessible(true);
			externalizers.add(Externalizer.of(field, cl));
		}
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
		final T object;
		try {
			object = clazz.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new ExternalizorException("Can't create an instance of " + clazz, e);
		}
		readExternal(object, in);
		return object;
	}

}

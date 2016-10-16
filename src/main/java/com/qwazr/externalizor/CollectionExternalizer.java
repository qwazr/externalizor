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
import java.util.Collection;
import java.util.Map;

interface CollectionExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> CollectionExternalizer<T, V> collection(final Field field, final Class<? extends T> clazz) {
		if (Map.class.isAssignableFrom(clazz))
			return (CollectionExternalizer<T, V>) new FieldMapExternalizer(field, clazz);
		if (Collection.class.isAssignableFrom(clazz))
			return (CollectionExternalizer<T, V>) new FieldCollectionExternalizer(field, clazz);
		// Can't handle this class, we return null
		return null;
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
			final Map map = constructor.newInstance();
			int size = in.readInt();
			while (size-- > 0)
				map.put(keyExternalizer.readObject(in), valueExternalizer.readObject(in));
			return map;
		}
	}

}

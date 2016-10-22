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

interface Externalizer<T, V> {

	static <T, V> Externalizer<T, V> of(final Class<? extends T> clazz) {
		Externalizer<T, V> externalizer;
		if ((externalizer = LangExternalizer.lang(clazz)) != null)
			return externalizer;
		if ((externalizer = TimeExternalizer.time(clazz)) != null)
			return externalizer;
		return (Externalizer<T, V>) ClassExternalizer.of(clazz);
	}

	static <T, V> Externalizer<T, V> of(final Field field, final Class<? extends T> clazz) {
		Externalizer<T, V> externalizer;
		if (clazz.isPrimitive())
			if ((externalizer = (Externalizer<T, V>) PrimitiveExternalizer.primitive(field, clazz)) != null)
				return externalizer;
		if (clazz.isArray())
			if ((externalizer = (Externalizer<T, V>) ArrayExternalizer.array(field, clazz)) != null)
				return externalizer;
		if ((externalizer = (Externalizer<T, V>) CollectionExternalizer.collection(field, clazz)) != null)
			return externalizer;
		if ((externalizer = (Externalizer<T, V>) LangExternalizer.lang(field, clazz)) != null)
			return externalizer;
		if ((externalizer = (Externalizer<T, V>) TimeExternalizer.time(field, clazz)) != null)
			return externalizer;
		return new FieldExternalizer.FieldParentExternalizer(field, ClassExternalizer.of(clazz));
	}

	void writeExternal(final T object, final ObjectOutput out) throws IOException, ReflectiveOperationException;

	void readExternal(final T object, final ObjectInput in) throws IOException, ReflectiveOperationException;

	V readObject(final ObjectInput in) throws IOException, ReflectiveOperationException;

}

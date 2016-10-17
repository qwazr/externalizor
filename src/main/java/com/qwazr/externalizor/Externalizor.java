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
import java.util.concurrent.ConcurrentHashMap;

public class Externalizor<T> {

	private final Externalizer<T, T> externalizer;
	private final Class<T> clazz;

	private Externalizor(final Class<T> clazz) {
		externalizer = new ClassExternalizer.RootExternalizer(clazz);
		this.clazz = clazz;
	}

	public void writeExternal(final T object, final ObjectOutput out) throws IOException {
		try {
			externalizer.writeExternal(object, out);
		} catch (ReflectiveOperationException e) {
			throw new ExternalizorException("Error while serializing the class " + clazz, e);
		}
	}

	void readExternal(final T object, final ObjectInput in) throws IOException, ClassNotFoundException {
		try {
			externalizer.readExternal(object, in);
		} catch (ReflectiveOperationException e) {
			throw new ExternalizorException("Error while unserializing the class " + clazz, e);
		}
	}

	private final static ConcurrentHashMap<Class<?>, Externalizor> externalizorMap = new ConcurrentHashMap();

	public final static <T> Externalizor<T> of(final Class<T> clazz) {
		return externalizorMap.computeIfAbsent(clazz, Externalizor::new);
	}

}

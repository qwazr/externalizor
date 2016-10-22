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

import java.io.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

	public void readExternal(final T object, final ObjectInput in) throws IOException, ClassNotFoundException {
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

	/**
	 * Serializes an Object to the specified stream using compression.
	 * <p>
	 * The stream passed in is not closed within this method. This is the responsibility of your application.
	 *
	 * @param object the object to serialize to bytes, must not be null
	 * @param output the stream to write to, must not be null
	 * @return
	 * @throws IOException          if the serialization fails
	 * @throws NullPointerException if object or output is null
	 */
	public static final void serialize(final Object object, final OutputStream output) throws IOException {
		Objects.requireNonNull(object, "The serializable object is null");
		Objects.requireNonNull(output, "The output stream is null");
		final Externalizor externalizor = of(object.getClass());
		try (final GZIPOutputStream compressed = new GZIPOutputStream(output)) {
			try (final ObjectOutputStream objected = new ObjectOutputStream(compressed)) {
				externalizor.writeExternal(object, objected);
			}
		}
	}

	/**
	 * Serializes an Object to the specified stream without compression.
	 * <p>
	 * The stream passed in is not closed within this method. This is the responsibility of your application.
	 *
	 * @param object the object to serialize to bytes, must not be null
	 * @param output the stream to write to, must not be null
	 * @return
	 * @throws IOException          if the serialization fails
	 * @throws NullPointerException if object or output is null
	 */
	public static final void serializeRaw(final Object object, final OutputStream output) throws IOException {
		Objects.requireNonNull(object, "The serializable object is null");
		Objects.requireNonNull(output, "The output stream is null");
		final Externalizor externalizor = of(object.getClass());
		try (final ObjectOutputStream objected = new ObjectOutputStream(output)) {
			externalizor.writeExternal(object, objected);
		}
	}

	/**
	 * Deserializes an Object from the specified stream.
	 * <p>
	 * The stream passed in is not closed within this method. This is the responsibility of your application.
	 *
	 * @param input the serialized object input stream, must not be null
	 * @param <T>   the object type to be deserialized
	 * @return
	 * @throws IOException                  if the deserialization fails
	 * @throws ReflectiveOperationException if the class instantiation fails
	 * @throws NullPointerException         if object or output is null
	 */
	public static final <T> T deserialize(final InputStream input, final Class<T> clazz)
			throws IOException, ReflectiveOperationException {
		Objects.requireNonNull(input, "The input stream is null");
		Objects.requireNonNull(input, "The class is null");
		final Externalizor externalizor = of(clazz);
		try (final GZIPInputStream compressed = new GZIPInputStream(input)) {
			try (final ObjectInputStream objected = new ObjectInputStream(compressed)) {
				final T object = clazz.newInstance();
				externalizor.readExternal(object, objected);
				return object;
			}
		}
	}

	/**
	 * Deserializes an Object from the specified stream without compression.
	 * <p>
	 * The stream passed in is not closed within this method. This is the responsibility of your application.
	 *
	 * @param input the serialized object input stream, must not be null
	 * @param <T>   the object type to be deserialized
	 * @return
	 * @throws IOException                  if the deserialization fails
	 * @throws ReflectiveOperationException if the class instantiation fails
	 * @throws NullPointerException         if object or output is null
	 */

	public static final <T> T deserializeRaw(final InputStream input, final Class<T> clazz)
			throws IOException, ReflectiveOperationException {
		Objects.requireNonNull(input, "The input stream is null");
		Objects.requireNonNull(input, "The class is null");
		final Externalizor externalizor = of(clazz);
		try (final ObjectInputStream objected = new ObjectInputStream(input)) {
			final T object = clazz.newInstance();
			externalizor.readExternal(object, objected);
			return object;
		}
	}
}

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

public class Externalizor {

	private final static ConcurrentHashMap<Class<?>, Externalizer> externalizerMap = new ConcurrentHashMap();

	public final static <T> Externalizer<T, T> of(final Class<T> clazz) {
		return externalizerMap.computeIfAbsent(clazz, aClass -> Externalizer.of(aClass));
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
	public static final void serialize(final Object object, final OutputStream output)
			throws IOException, ReflectiveOperationException {
		Objects.requireNonNull(object, "The serializable object is null");
		Objects.requireNonNull(output, "The output stream is null");
		final Externalizer externalizer = of(object.getClass());
		try (final GZIPOutputStream compressed = new GZIPOutputStream(output)) {
			try (final ObjectOutputStream objected = new ObjectOutputStream(compressed)) {
				externalizer.writeExternal(object, objected);
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
	public static final void serializeRaw(final Object object, final OutputStream output)
			throws IOException, ReflectiveOperationException {
		Objects.requireNonNull(object, "The serializable object is null");
		Objects.requireNonNull(output, "The output stream is null");
		final Externalizer externalizer = of(object.getClass());
		try (final ObjectOutputStream objected = new ObjectOutputStream(output)) {
			externalizer.writeExternal(object, objected);
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
		final Externalizer<T, T> externalizer = of(clazz);
		try (final GZIPInputStream compressed = new GZIPInputStream(input)) {
			try (final ObjectInputStream objected = new ObjectInputStream(compressed)) {
				return externalizer.readObject(objected);
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
		final Externalizer<T, T> externalizer = of(clazz);
		try (final ObjectInputStream objected = new ObjectInputStream(input)) {
			return externalizer.readObject(objected);

		}
	}
}

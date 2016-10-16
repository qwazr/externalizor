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

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class SerialInner implements Externalizable {

	private final String key;
	private final String value;

	public SerialInner() {
		key = RandomStringUtils.randomAscii(8);
		value = RandomStringUtils.randomAscii(8);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Serial))
			return false;
		final SerialInner s = (SerialInner) o;
		return Objects.equals(key, s.key) && Objects.equals(value, s.value);
	}

	// The serialization part
	
	private final static Externalizor<SerialInner> externalizor = Externalizor.of(SerialInner.class);

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		externalizor.writeExternal(this, out);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		externalizor.readExternal(this, in);
	}
}

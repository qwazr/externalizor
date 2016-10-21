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
import org.apache.commons.lang3.RandomUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

public class ComplexExternal extends SimpleLang.External implements ComplexInterface, Externalizable {

	final public SimpleCollection.External collection;
	final public SimpleCollection.External nullObject;
	final public HashMap<String, SimplePrimitive.External> mapObject;
	final private SimpleTime.External timeObject;
	transient String transientValue;

	public ComplexExternal() {

		nullObject = null;
		collection = new SimpleCollection.External();
		mapObject = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(2, 5); i++)
			mapObject.put(RandomStringUtils.randomAscii(5), new SimplePrimitive.External());
		timeObject = new SimpleTime.External();
		transientValue = RandomStringUtils.randomAscii(12);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ComplexExternal))
			return false;
		if (!ComplexInterface.equals(this, (ComplexInterface) o))
			return false;
		return super.equals(o);
	}

	@Override
	public SimpleCollection getNullObject() {
		return nullObject;
	}

	@Override
	public SimpleCollection getCollection() {
		return collection;
	}

	@Override
	public HashMap<String, ? extends SimplePrimitive> getMapObject() {
		return mapObject;
	}

	@Override
	public SimpleTime getTimeObject() {
		return timeObject;
	}

	// The serialization part

	private final static Externalizor<ComplexExternal> externalizor = Externalizor.of(ComplexExternal.class);

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		externalizor.writeExternal(this, out);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		externalizor.readExternal(this, in);

	}

}

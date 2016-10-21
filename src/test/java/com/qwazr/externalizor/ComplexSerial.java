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

import java.io.Serializable;
import java.util.HashMap;

public class ComplexSerial extends SimpleLang implements ComplexInterface, Serializable {

	final public SimpleCollection collection;
	final public SimpleCollection nullObject;
	final public HashMap<String, SimplePrimitive> mapObject;
	final private SimpleTime timeObject;
	transient String transientValue;

	public ComplexSerial() {

		nullObject = null;

		collection = new SimpleCollection();

		mapObject = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(2, 5); i++)
			mapObject.put(RandomStringUtils.randomAscii(5), new SimplePrimitive());

		timeObject = new SimpleTime();

		transientValue = RandomStringUtils.randomAscii(12);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ComplexSerial))
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

}

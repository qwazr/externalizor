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

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Objects;

public class AbstractProperty implements Serializable {

	private final String test;

	/**
	 * This type is not natively supported
	 */
	private final Pair pair = Pair.of("Key", "Value");

	public AbstractProperty(String test) {
		this.test = test;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof AbstractProperty))
			return false;
		AbstractProperty p = (AbstractProperty) o;
		if (!Objects.equals(test, p.test))
			return false;
		if (!Objects.equals(pair, p.pair))
			return false;
		return true;
	}
}

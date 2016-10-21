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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public interface ComplexInterface extends Serializable {

	SimpleCollection getNullObject();

	SimpleCollection getCollection();

	HashMap<String, ? extends SimplePrimitive> getMapObject();

	SimpleTime getTimeObject();

	static boolean equals(ComplexInterface s1, ComplexInterface s2) {
		if (s1 == null && s2 == null)
			return true;

		if (s1.getNullObject() != s2.getNullObject())
			return false;

		if (!Objects.equals(s1.getCollection(), s2.getCollection()))
			return false;

		if (!Objects.deepEquals(s1.getMapObject(), s2.getMapObject()))
			return false;

		if (!Objects.equals(s1.getTimeObject(), s2.getTimeObject()))
			return false;

		return true;
	}
}

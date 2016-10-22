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
import java.util.*;

public class AbstractCollection implements Serializable {

	private final Map<String, String> abstractMap;
	private final Set<String> abstractSet;
	private final List<String> abstractList;

	public AbstractCollection() {
		this.abstractMap = new HashMap<>();
		this.abstractSet = new HashSet<>();
		this.abstractList = new ArrayList<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++) {
			abstractMap.put(RandomStringUtils.randomAlphanumeric(4), RandomStringUtils.randomAlphanumeric(5));
			abstractSet.add(RandomStringUtils.randomAlphanumeric(6));
			abstractList.add(RandomStringUtils.randomAlphanumeric(7));
		}

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof AbstractCollection))
			return false;
		final AbstractCollection c = (AbstractCollection) o;
		return Objects.deepEquals(abstractMap, c.abstractMap) && Objects.deepEquals(abstractSet, c.abstractSet)
				&& Objects.deepEquals(abstractList, c.abstractList);
	}
}

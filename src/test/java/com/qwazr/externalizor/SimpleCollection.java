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

import java.util.*;

public class SimpleCollection extends AutoExternalizor {

	final public ArrayList<String> stringList;
	final public ArrayList<String> nullList;

	final public LinkedHashSet<Integer> integerSet;
	final public TreeSet<Character> nullSet;

	final protected Vector<Byte> byteList;

	final private HashMap<Short, Long> mapShortLong;
	final private TreeMap<String, Boolean> nullMap;

	public SimpleCollection() {

		nullList = null;
		stringList = new ArrayList<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			stringList.add(RandomStringUtils.randomAscii(8));

		nullSet = null;
		integerSet = new LinkedHashSet<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			integerSet.add(RandomUtils.nextInt(0, Integer.MAX_VALUE));

		byteList = new Vector<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			byteList.add((byte) RandomUtils.nextInt(0, 128));

		nullMap = null;
		mapShortLong = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapShortLong.put((short) RandomUtils.nextInt(0, Short.MAX_VALUE), RandomUtils.nextLong(0, Long.MAX_VALUE));

	}

	@Override
	public boolean equals(Object o) {

		if (o == null || !(o instanceof SimpleCollection))
			return false;
		final SimpleCollection s = (SimpleCollection) o;

		if (nullList != s.nullList)
			return false;
		if (!Objects.deepEquals(stringList, stringList))
			return false;

		if (nullSet != s.nullSet)
			return false;
		if (!Objects.deepEquals(integerSet, integerSet))
			return false;

		if (!Objects.deepEquals(byteList, byteList))
			return false;

		if (nullMap != s.nullMap)
			return false;
		if (!Objects.deepEquals(mapShortLong, mapShortLong))
			return false;

		return true;
	}

}

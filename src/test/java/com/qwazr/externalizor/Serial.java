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

import com.qwazr.utils.CollectionsUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;

public class Serial implements Externalizable {

	final private int integerValue;
	public long longValue;
	final public String string;
	public String emptyObject;
	public double[] primitiveArray;
	String[] objectArray;
	final public ArrayList<String> stringList;
	final public LinkedHashSet<Integer> integerList;
	final public TreeSet<Long> longList;
	final public ArrayList<Float> floatList;
	final public ArrayList<Double> doubleList;
	final protected Vector<Byte> byteList;
	final public HashSet<Short> shortList;
	final public HashSet<Character> charList;
	final private HashMap<String, Integer> mapStringInteger;
	final private HashMap<Byte, Character> mapByteChar;
	final private HashMap<Short, Long> mapShortLong;
	final private TreeMap<Float, Double> mapFloatDouble;
	final private TreeMap<String, Boolean> mapStringBoolean;
	public SerialInner inner = new SerialInner();
	transient String transientValue;

	public Serial() {
		integerValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		longValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		string = RandomStringUtils.randomAscii(64);
		emptyObject = null;
		primitiveArray = new double[] { RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE) };
		objectArray = new String[] { RandomStringUtils.randomAscii(8),
				RandomStringUtils.randomAscii(8),
				RandomStringUtils.randomAscii(8) };
		stringList = new ArrayList<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			stringList.add(RandomStringUtils.randomAscii(8));
		integerList = new LinkedHashSet<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			integerList.add(RandomUtils.nextInt(0, Integer.MAX_VALUE));
		longList = new TreeSet<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			longList.add(RandomUtils.nextLong(0, Long.MAX_VALUE));
		floatList = new ArrayList<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			floatList.add(RandomUtils.nextFloat(0, Float.MAX_VALUE));
		doubleList = new ArrayList<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			doubleList.add(RandomUtils.nextDouble(0, Double.MAX_VALUE));
		byteList = new Vector<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			byteList.add((byte) RandomUtils.nextInt(0, 128));
		shortList = new HashSet<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			shortList.add((short) RandomUtils.nextInt(0, Short.MAX_VALUE));
		charList = new HashSet<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			charList.add((char) RandomUtils.nextInt(0, Short.MAX_VALUE));
		mapStringInteger = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapStringInteger.put(RandomStringUtils.randomAscii(5), RandomUtils.nextInt(0, Integer.MAX_VALUE));
		mapByteChar = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapByteChar.put((byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
					(char) RandomUtils.nextInt(0, Character.MAX_VALUE));
		mapShortLong = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapShortLong.put((short) RandomUtils.nextInt(0, Short.MAX_VALUE), RandomUtils.nextLong(0, Long.MAX_VALUE));
		mapFloatDouble = new TreeMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapFloatDouble.put(RandomUtils.nextFloat(0, Float.MAX_VALUE), RandomUtils.nextDouble(0, Double.MAX_VALUE));
		mapStringBoolean = new TreeMap<>();
		for (int i = 0; i < RandomUtils.nextInt(5, 10); i++)
			mapStringBoolean.put(RandomStringUtils.randomAscii(5), RandomUtils.nextInt(0, 1) == 1);
		transientValue = RandomStringUtils.randomAscii(12);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Serial))
			return false;
		final Serial s = (Serial) o;
		if (!Objects.equals(string, s.string))
			return false;
		if (!Objects.equals(emptyObject, s.emptyObject))
			return false;
		if (!Objects.deepEquals(primitiveArray, s.primitiveArray))
			return false;
		if (!Objects.deepEquals(objectArray, s.objectArray))
			return false;
		if (!CollectionsUtils.equals(stringList, s.stringList))
			return false;
		if (!CollectionsUtils.equals(integerList, s.integerList))
			return false;
		if (!CollectionsUtils.equals(longList, s.longList))
			return false;
		if (!CollectionsUtils.equals(floatList, s.floatList))
			return false;
		if (!CollectionsUtils.equals(doubleList, s.doubleList))
			return false;
		if (!CollectionsUtils.equals(byteList, s.byteList))
			return false;
		if (!CollectionsUtils.equals(charList, s.charList))
			return false;
		if (!CollectionsUtils.equals(shortList, s.shortList))
			return false;
		if (!CollectionsUtils.equals(mapStringInteger, s.mapStringInteger))
			return false;
		if (!CollectionsUtils.equals(mapByteChar, s.mapByteChar))
			return false;
		if (!CollectionsUtils.equals(mapShortLong, s.mapShortLong))
			return false;
		if (!CollectionsUtils.equals(mapFloatDouble, s.mapFloatDouble))
			return false;
		if (!CollectionsUtils.equals(mapStringBoolean, s.mapStringBoolean))
			return false;
		if (!Objects.equals(inner, s.inner))
			return false;
		return true;
	}

	// The serialization part

	private final static Externalizor<Serial> externalizor = Externalizor.of(Serial.class);

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		externalizor.writeExternal(this, out);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		externalizor.readExternal(this, in);
	}
}

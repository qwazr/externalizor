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
import org.apache.commons.lang3.StringUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;

public class Serial extends SerialInner implements Externalizable {

	final private int integerValue;
	public long longValue;
	final public String string;
	final public String nullString = null;
	final public String emptyString = StringUtils.EMPTY;
	public double[] doubleArray;
	public double[] emptyDoubleArray = new double[0];
	public Double[] doubleLangArray;
	public float[] floatArray;
	public Float[] floatLangArray;
	public int[] intArray;
	public Integer[] intLangArray;
	public short[] shortArray;
	public Short[] shortLangArray;
	public long[] longArray;
	public Long[] longLangArray;
	public byte[] byteArray;
	public Byte[] byteLangArray;
	public char[] charArray;
	public Character[] charLangArray;
	public boolean[] booleanArray;
	public boolean[] emptyBooleanArray = new boolean[0];
	public Boolean[] booleanLangArray;

	String[] stringArray;
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
	final private HashMap<String, String> nullMap = null;
	final private ArrayList<String> nullList = null;
	final public SerialInner inner = new SerialInner();
	final public SerialInner nullObject = null;
	final public HashMap<String, SerialInner> mapObject;
	transient String transientValue;

	public Serial() {
		integerValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		longValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		string = RandomStringUtils.randomAscii(64);
		doubleArray = new double[] { RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE) };
		doubleLangArray = new Double[] { RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE),
				RandomUtils.nextDouble(0, Double.MAX_VALUE) };
		floatArray = new float[] { RandomUtils.nextFloat(0, Float.MAX_VALUE),
				RandomUtils.nextFloat(0, Float.MAX_VALUE),
				RandomUtils.nextFloat(0, Float.MAX_VALUE) };
		floatLangArray = new Float[] { RandomUtils.nextFloat(0, Float.MAX_VALUE),
				RandomUtils.nextFloat(0, Float.MAX_VALUE),
				RandomUtils.nextFloat(0, Float.MAX_VALUE) };
		intArray = new int[] { RandomUtils.nextInt(0, Integer.MAX_VALUE),
				RandomUtils.nextInt(0, Integer.MAX_VALUE),
				RandomUtils.nextInt(0, Integer.MAX_VALUE) };
		intLangArray = new Integer[] { RandomUtils.nextInt(0, Integer.MAX_VALUE),
				RandomUtils.nextInt(0, Integer.MAX_VALUE),
				RandomUtils.nextInt(0, Integer.MAX_VALUE) };
		shortArray = new short[] { (short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE) };
		shortLangArray = new Short[] { (short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE) };
		longArray = new long[] { RandomUtils.nextLong(0, Long.MAX_VALUE),
				RandomUtils.nextLong(0, Long.MAX_VALUE),
				RandomUtils.nextLong(0, Long.MAX_VALUE) };
		longLangArray = new Long[] { RandomUtils.nextLong(0, Long.MAX_VALUE),
				RandomUtils.nextLong(0, Long.MAX_VALUE),
				RandomUtils.nextLong(0, Long.MAX_VALUE) };
		charArray = new char[] { (char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE) };
		charLangArray = new Character[] { (char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE) };
		byteArray = new byte[] { (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE) };
		byteLangArray = new Byte[] { (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE) };
		booleanArray = new boolean[] { RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1 };
		booleanLangArray = new Boolean[] { RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1 };
		stringArray = new String[] { RandomStringUtils.randomAscii(8),
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
		mapObject = new HashMap<>();
		for (int i = 0; i < RandomUtils.nextInt(2, 5); i++)
			mapObject.put(RandomStringUtils.randomAscii(5), new SerialInner());
		transientValue = RandomStringUtils.randomAscii(12);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Serial))
			return false;
		final Serial s = (Serial) o;
		if (!Objects.equals(string, s.string))
			return false;
		if (!Objects.equals(nullString, s.nullString))
			return false;
		if (!Objects.equals(emptyString, s.emptyString))
			return false;
		if (!Objects.deepEquals(stringArray, s.stringArray))
			return false;
		if (!Objects.deepEquals(intArray, s.intArray))
			return false;
		if (!Objects.deepEquals(intLangArray, s.intLangArray))
			return false;
		if (!Objects.deepEquals(longArray, s.longArray))
			return false;
		if (!Objects.deepEquals(longLangArray, s.longLangArray))
			return false;
		if (!Objects.deepEquals(shortArray, s.shortArray))
			return false;
		if (!Objects.deepEquals(shortLangArray, s.shortLangArray))
			return false;
		if (!Objects.deepEquals(floatArray, s.floatArray))
			return false;
		if (!Objects.deepEquals(floatLangArray, s.floatLangArray))
			return false;
		if (!Objects.deepEquals(emptyDoubleArray, s.emptyDoubleArray))
			return false;
		if (!Objects.deepEquals(doubleArray, s.doubleArray))
			return false;
		if (!Objects.deepEquals(doubleLangArray, s.doubleLangArray))
			return false;
		if (!Objects.deepEquals(booleanArray, s.booleanArray))
			return false;
		if (!Objects.deepEquals(booleanLangArray, s.booleanLangArray))
			return false;
		if (!Objects.deepEquals(emptyBooleanArray, s.emptyBooleanArray))
			return false;
		if (!Objects.deepEquals(byteArray, s.byteArray))
			return false;
		if (!Objects.deepEquals(byteLangArray, s.byteLangArray))
			return false;
		if (!Objects.deepEquals(charArray, s.charArray))
			return false;
		if (!Objects.deepEquals(charLangArray, s.charLangArray))
			return false;
		if (!Objects.equals(stringList, s.stringList))
			return false;
		if (!Objects.equals(integerList, s.integerList))
			return false;
		if (!Objects.equals(longList, s.longList))
			return false;
		if (!Objects.equals(floatList, s.floatList))
			return false;
		if (!Objects.equals(doubleList, s.doubleList))
			return false;
		if (!Objects.equals(byteList, s.byteList))
			return false;
		if (!Objects.equals(charList, s.charList))
			return false;
		if (!Objects.equals(shortList, s.shortList))
			return false;
		if (!Objects.equals(mapStringInteger, s.mapStringInteger))
			return false;
		if (!Objects.equals(mapByteChar, s.mapByteChar))
			return false;
		if (!Objects.equals(mapShortLong, s.mapShortLong))
			return false;
		if (!Objects.equals(mapFloatDouble, s.mapFloatDouble))
			return false;
		if (!Objects.equals(mapStringBoolean, s.mapStringBoolean))
			return false;
		if (!Objects.equals(nullMap, s.nullMap))
			return false;
		if (!Objects.equals(nullList, s.nullList))
			return false;
		if (!Objects.equals(nullObject, s.nullObject))
			return false;
		if (!Objects.equals(inner, s.inner))
			return false;
		if (!Objects.equals(mapObject, s.mapObject))
			return false;
		return super.equals(s);
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

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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SimpleLang implements Serializable {

	final public String emptyString;
	private final String stringValue;
	private final String stringNullValue;
	private final String[] stringArray;
	private final ArrayList<String> stringList;

	private final Integer intLangValue;
	private final Integer intNullValue;
	private final Integer[] intArray;
	private final ArrayList<Integer> intList;

	private final Short shortLangValue;
	private final Short shortNullValue;
	private final Short[] shortArray;
	private final ArrayList<Short> shortList;

	private final Long longLangValue;
	private final Long longNullValue;
	private final Long[] longArray;
	private final ArrayList<Long> longList;

	private final Float floatLangValue;
	private final Float floatNullValue;
	private final Float[] floatArray;
	private final ArrayList<Float> floatList;

	private final Double doubleLangValue;
	private final Double doubleNullValue;
	private final Double[] doubleArray;
	private final ArrayList<Double> doubleList;

	private final Boolean booleanLangValue;
	private final Boolean booleanNullValue;
	private final Boolean[] booleanArray;
	private final ArrayList<Boolean> booleanList;

	private final Byte byteLangValue;
	private final Byte byteNullValue;
	private final Byte[] byteArray;
	private final ArrayList<Byte> byteList;

	private final Character charLangValue;
	private final Character charNullValue;
	private final Character[] charArray;
	private final ArrayList<Character> charList;

	public enum EnumType {
		on, off
	}

	private final EnumType enumNull;
	private final EnumType enumValue;
	private final EnumType[] enumArray;
	private final ArrayList<EnumType> enumList;

	public SimpleLang() {

		emptyString = StringUtils.EMPTY;

		stringValue = RandomStringUtils.randomAscii(8);
		stringNullValue = null;
		stringArray = new String[RandomUtils.nextInt(5, 20)];
		stringArray[0] = null;
		for (int i = 1; i < stringArray.length; i++)
			stringArray[i] = RandomStringUtils.randomAscii(5);
		stringList = new ArrayList(Arrays.asList(stringArray));

		intLangValue = RandomUtils.nextInt();
		intNullValue = null;
		intArray = new Integer[RandomUtils.nextInt(5, 20)];
		intArray[0] = null;
		for (int i = 1; i < intArray.length; i++)
			intArray[i] = RandomUtils.nextInt();
		intList = new ArrayList(Arrays.asList(intArray));

		shortLangValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortNullValue = null;
		shortArray = new Short[RandomUtils.nextInt(5, 20)];
		shortArray[0] = null;
		for (int i = 1; i < shortArray.length; i++)
			shortArray[i] = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortList = new ArrayList(Arrays.asList(shortArray));

		longLangValue = RandomUtils.nextLong();
		longNullValue = null;
		longArray = new Long[RandomUtils.nextInt(5, 20)];
		longArray[0] = null;
		for (int i = 1; i < longArray.length; i++)
			longArray[i] = RandomUtils.nextLong();
		longList = new ArrayList(Arrays.asList(longArray));

		floatLangValue = (float) RandomUtils.nextFloat();
		floatNullValue = null;
		floatArray = new Float[RandomUtils.nextInt(5, 20)];
		floatArray[0] = null;
		for (int i = 1; i < floatArray.length; i++)
			floatArray[i] = RandomUtils.nextFloat();
		floatList = new ArrayList(Arrays.asList(floatArray));

		doubleLangValue = RandomUtils.nextDouble();
		doubleNullValue = null;
		doubleArray = new Double[RandomUtils.nextInt(5, 20)];
		doubleArray[0] = null;
		for (int i = 1; i < doubleArray.length; i++)
			doubleArray[i] = RandomUtils.nextDouble();
		doubleList = new ArrayList(Arrays.asList(doubleArray));

		booleanLangValue = RandomUtils.nextInt(0, 1) == 0;
		booleanNullValue = null;
		booleanArray = new Boolean[RandomUtils.nextInt(5, 20)];
		booleanArray[0] = null;
		for (int i = 1; i < booleanArray.length; i++)
			booleanArray[i] = RandomUtils.nextBoolean();
		booleanList = new ArrayList(Arrays.asList(booleanArray));

		byteLangValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteNullValue = null;
		byteArray = new Byte[RandomUtils.nextInt(5, 20)];
		byteArray[0] = null;
		for (int i = 1; i < byteArray.length; i++)
			byteArray[i] = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteList = new ArrayList(Arrays.asList(byteArray));

		charLangValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charNullValue = null;
		charArray = new Character[RandomUtils.nextInt(5, 20)];
		charArray[0] = null;
		for (int i = 1; i < charArray.length; i++)
			charArray[i] = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charList = new ArrayList(Arrays.asList(charArray));

		enumNull = null;
		enumValue = RandomUtils.nextInt(0, 1) == 0 ? EnumType.on : EnumType.off;
		enumArray = new EnumType[RandomUtils.nextInt(5, 20)];
		enumArray[0] = null;
		for (int i = 1; i < enumArray.length; i++)
			enumArray[i] = RandomUtils.nextInt(0, 1) == 0 ? EnumType.on : EnumType.off;
		enumList = new ArrayList(Arrays.asList(enumArray));
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SimpleLang))
			return false;
		final SimpleLang s = (SimpleLang) o;

		if (!Objects.equals(emptyString, s.emptyString))
			return false;
		if (!Objects.equals(stringValue, s.stringValue))
			return false;
		if (!Objects.equals(stringNullValue, s.stringNullValue))
			return false;
		if (!Arrays.equals(stringArray, s.stringArray))
			return false;
		if (!Objects.equals(stringList, s.stringList))
			return false;

		if (!Objects.equals(intLangValue, s.intLangValue))
			return false;
		if (!Objects.equals(intNullValue, s.intNullValue))
			return false;
		if (!Arrays.equals(intArray, s.intArray))
			return false;
		if (!Objects.equals(intList, s.intList))
			return false;

		if (!Objects.equals(shortLangValue, s.shortLangValue))
			return false;
		if (!Objects.equals(shortNullValue, s.shortNullValue))
			return false;
		if (!Arrays.equals(shortArray, s.shortArray))
			return false;
		if (!Objects.equals(shortList, s.shortList))
			return false;

		if (!Objects.equals(longNullValue, s.longNullValue))
			return false;
		if (!Objects.equals(longLangValue, s.longLangValue))
			return false;
		if (!Arrays.equals(longArray, s.longArray))
			return false;
		if (!Objects.equals(longList, s.longList))
			return false;

		if (!Objects.equals(floatNullValue, s.floatNullValue))
			return false;
		if (!Objects.equals(floatLangValue, s.floatLangValue))
			return false;
		if (!Arrays.equals(floatArray, s.floatArray))
			return false;
		if (!Objects.equals(floatList, s.floatList))
			return false;

		if (!Objects.equals(doubleNullValue, s.doubleNullValue))
			return false;
		if (!Objects.equals(doubleLangValue, s.doubleLangValue))
			return false;
		if (!Arrays.equals(doubleArray, s.doubleArray))
			return false;
		if (!Objects.equals(doubleList, s.doubleList))
			return false;

		if (!Objects.equals(booleanNullValue, s.booleanNullValue))
			return false;
		if (!Objects.equals(booleanLangValue, s.booleanLangValue))
			return false;
		if (!Arrays.equals(booleanArray, s.booleanArray))
			return false;
		if (!Objects.equals(booleanList, s.booleanList))
			return false;

		if (!Objects.equals(byteNullValue, s.byteNullValue))
			return false;
		if (!Objects.equals(byteLangValue, s.byteLangValue))
			return false;
		if (!Arrays.equals(byteArray, s.byteArray))
			return false;
		if (!Objects.equals(byteList, s.byteList))
			return false;

		if (!Objects.equals(charNullValue, s.charNullValue))
			return false;
		if (!Objects.equals(charLangValue, s.charLangValue))
			return false;
		if (!Arrays.equals(charArray, s.charArray))
			return false;
		if (!Objects.equals(charList, s.charList))
			return false;

		if (!Objects.equals(enumNull, s.enumNull))
			return false;
		if (!Objects.equals(enumValue, s.enumValue))
			return false;
		if (!Arrays.equals(enumArray, s.enumArray))
			return false;
		if (!Objects.equals(enumList, s.enumList))
			return false;

		return true;
	}

}

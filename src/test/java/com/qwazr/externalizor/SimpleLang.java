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
		stringArray = new String[] { stringNullValue, stringValue };
		stringList = new ArrayList(Arrays.asList(stringValue, stringNullValue));

		intLangValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		intNullValue = null;
		intArray = new Integer[] { intNullValue, intLangValue };
		intList = new ArrayList(Arrays.asList(intLangValue, intNullValue));

		shortLangValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortNullValue = null;
		shortArray = new Short[] { shortNullValue, shortLangValue };
		shortList = new ArrayList(Arrays.asList(shortLangValue, shortNullValue));

		longLangValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		longNullValue = null;
		longArray = new Long[] { longNullValue, longLangValue };
		longList = new ArrayList(Arrays.asList(longLangValue, longNullValue));

		floatLangValue = (float) RandomUtils.nextDouble(0, Float.MAX_VALUE);
		floatNullValue = null;
		floatArray = new Float[] { floatLangValue, floatNullValue };
		floatList = new ArrayList(Arrays.asList(floatNullValue, floatLangValue));

		doubleLangValue = RandomUtils.nextDouble(0, Double.MAX_VALUE);
		doubleNullValue = null;
		doubleArray = new Double[] { doubleLangValue, doubleNullValue };
		doubleList = new ArrayList(Arrays.asList(doubleNullValue, doubleLangValue));

		booleanLangValue = RandomUtils.nextInt(0, 1) == 0;
		booleanNullValue = null;
		booleanArray = new Boolean[] { booleanLangValue, booleanNullValue };
		booleanList = new ArrayList(Arrays.asList(booleanNullValue, booleanLangValue));

		byteLangValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteNullValue = null;
		byteArray = new Byte[] { byteLangValue, byteNullValue };
		byteList = new ArrayList(Arrays.asList(byteNullValue, byteLangValue));

		charLangValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charNullValue = null;
		charArray = new Character[] { charLangValue, charNullValue };
		charList = new ArrayList(Arrays.asList(charNullValue, charLangValue));

		enumNull = null;
		enumValue = RandomUtils.nextInt(0, 1) == 0 ? EnumType.on : EnumType.off;
		enumArray = new EnumType[] { enumNull, enumValue };
		enumList = new ArrayList(Arrays.asList(enumValue, enumNull));
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

	public static class External extends SimpleLang implements Externalizable {

		private final static Externalizor<External> externalizor = Externalizor.of(External.class);

		@Override
		public void writeExternal(final ObjectOutput out) throws IOException {
			externalizor.writeExternal(this, out);
		}

		@Override
		public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
			externalizor.readExternal(this, in);

		}
	}
	
}

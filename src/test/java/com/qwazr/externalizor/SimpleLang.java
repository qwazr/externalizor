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

import java.util.Objects;

public class SimpleLang extends AutoExternalizor {

	private final String stringValue;
	private final String stringNullValue;
	private final Integer intLangValue;
	private final Integer intNullValue;
	private final Short shortLangValue;
	private final Short shortNullValue;
	private final Long longLangValue;
	private final Long longNullValue;
	private final Float floatLangValue;
	private final Float floatNullValue;
	private final Double doubleLangValue;
	private final Double doubleNullValue;
	private final Boolean booleanLangValue;
	private final Boolean booleanNullValue;
	private final Byte byteLangValue;
	private final Byte byteNullValue;
	private final Character charLangValue;
	private final Character charNullValue;

	public enum EnumType {
		on, off
	}

	private final EnumType enumNull;
	private final EnumType enumValue;

	public SimpleLang() {
		stringValue = RandomStringUtils.randomAscii(8);
		stringNullValue = null;
		intLangValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		intNullValue = null;
		shortLangValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortNullValue = null;
		longLangValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		longNullValue = null;
		floatLangValue = (float) RandomUtils.nextDouble(0, Float.MAX_VALUE);
		floatNullValue = null;
		doubleLangValue = RandomUtils.nextDouble(0, Double.MAX_VALUE);
		doubleNullValue = null;
		booleanLangValue = RandomUtils.nextInt(0, 1) == 0;
		booleanNullValue = null;
		byteLangValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteNullValue = null;
		charLangValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charNullValue = null;
		enumNull = null;
		enumValue = RandomUtils.nextInt(0, 1) == 0 ? EnumType.on : EnumType.off;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SimpleLang))
			return false;
		final SimpleLang s = (SimpleLang) o;

		if (!Objects.equals(stringValue, s.stringValue))
			return false;
		if (!Objects.equals(stringNullValue, s.stringNullValue))
			return false;
		if (!Objects.equals(intLangValue, s.intLangValue))
			return false;
		if (!Objects.equals(intNullValue, s.intNullValue))
			return false;
		if (!Objects.equals(shortLangValue, s.shortLangValue))
			return false;
		if (!Objects.equals(shortNullValue, s.shortNullValue))
			return false;
		if (!Objects.equals(longNullValue, s.longNullValue))
			return false;
		if (!Objects.equals(longLangValue, s.longLangValue))
			return false;
		if (!Objects.equals(floatNullValue, s.floatNullValue))
			return false;
		if (!Objects.equals(floatLangValue, s.floatLangValue))
			return false;
		if (!Objects.equals(doubleNullValue, s.doubleNullValue))
			return false;
		if (!Objects.equals(doubleLangValue, s.doubleLangValue))
			return false;
		if (!Objects.equals(booleanNullValue, s.booleanNullValue))
			return false;
		if (!Objects.equals(booleanLangValue, s.booleanLangValue))
			return false;
		if (!Objects.equals(byteNullValue, s.byteNullValue))
			return false;
		if (!Objects.equals(byteLangValue, s.byteLangValue))
			return false;
		if (!Objects.equals(charNullValue, s.charNullValue))
			return false;
		if (!Objects.equals(charLangValue, s.charLangValue))
			return false;
		if (!Objects.equals(enumNull, s.enumNull))
			return false;
		if (!Objects.equals(enumValue, s.enumValue))
			return false;
		return true;
	}


}

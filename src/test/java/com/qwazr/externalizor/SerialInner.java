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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class SerialInner extends AutoExternalizor {

	private final String stringValue;
	private final String stringNullValue;
	private final int intValue;
	private final Integer intLangValue;
	private final Integer intNullValue;
	private final short shortValue;
	private final Short shortLangValue;
	private final Short shortNullValue;
	private final long longValue;
	private final Long longLangValue;
	private final Long longNullValue;
	private final float floatValue;
	private final Float floatLangValue;
	private final Float floatNullValue;
	private final double doubleValue;
	private final Double doubleLangValue;
	private final Double doubleNullValue;
	private final boolean booleanValue;
	private final Boolean booleanLangValue;
	private final Boolean booleanNullValue;
	private final byte byteValue;
	private final Byte byteLangValue;
	private final Byte byteNullValue;
	private final char charValue;
	private final Character charLangValue;
	private final Character charNullValue;
	private final Date dateValue;
	private final LocalTime localTimeValue;
	private final LocalDate localDateValue;
	private final LocalDateTime localDateTimeValue;

	public enum Status {
		on, off
	}

	private final Status nullEnum;
	private final Status statusEnum;
	private final HashSet<Status> statusSet;

	public SerialInner() {
		stringValue = RandomStringUtils.randomAscii(8);
		stringNullValue = null;
		intValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		intLangValue = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		intNullValue = null;
		shortValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortLangValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortNullValue = null;
		longValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		longLangValue = RandomUtils.nextLong(0, Long.MAX_VALUE);
		longNullValue = null;
		floatValue = (float) RandomUtils.nextDouble(0, Float.MAX_VALUE);
		floatLangValue = (float) RandomUtils.nextDouble(0, Float.MAX_VALUE);
		floatNullValue = null;
		doubleValue = RandomUtils.nextDouble(0, Double.MAX_VALUE);
		doubleLangValue = RandomUtils.nextDouble(0, Double.MAX_VALUE);
		doubleNullValue = null;
		booleanValue = RandomUtils.nextInt(0, 1) == 0;
		booleanLangValue = RandomUtils.nextInt(0, 1) == 0;
		booleanNullValue = null;
		byteValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteLangValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteNullValue = null;
		charValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charLangValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charNullValue = null;
		nullEnum = null;
		statusEnum = RandomUtils.nextInt(0, 1) == 0 ? Status.on : Status.off;
		statusSet = new HashSet<>();
		for (int i = 0; i < RandomUtils.nextInt(0, 5); i++)
			statusSet.add(RandomUtils.nextInt(0, 1) == 0 ? Status.on : Status.off);
		dateValue = new Date(RandomUtils.nextLong(0, System.currentTimeMillis()));
		localTimeValue = LocalTime.of(RandomUtils.nextInt(0, 24), RandomUtils.nextInt(0, 60));
		localDateValue =
				LocalDate.of(RandomUtils.nextInt(0, Year.MAX_VALUE), RandomUtils.nextInt(1, 13),
						RandomUtils.nextInt(1, 29));
		localDateTimeValue = LocalDateTime
				.of(RandomUtils.nextInt(0, Year.MAX_VALUE), RandomUtils.nextInt(1, 13),
						RandomUtils.nextInt(1, 29), RandomUtils.nextInt(0, 24), RandomUtils.nextInt(0, 60));
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SerialInner))
			return false;
		final SerialInner s = (SerialInner) o;
		if (intValue != s.intValue)
			return false;
		if (shortValue != s.shortValue)
			return false;
		if (longValue != s.longValue)
			return false;
		if (floatValue != s.floatValue)
			return false;
		if (doubleValue != s.doubleValue)
			return false;
		if (booleanValue != s.booleanValue)
			return false;
		if (byteValue != s.byteValue)
			return false;
		if (charValue != s.charValue)
			return false;
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
		if (!Objects.equals(nullEnum, s.nullEnum))
			return false;
		if (!Objects.equals(statusEnum, s.statusEnum))
			return false;
		if (!Objects.equals(statusSet, s.statusSet))
			return false;
		if (!Objects.equals(dateValue, s.dateValue))
			return false;
		if (!Objects.equals(localDateValue, s.localDateValue))
			return false;
		if (!Objects.equals(localTimeValue, s.localTimeValue))
			return false;
		if (!Objects.equals(localDateTimeValue, s.localDateTimeValue))
			return false;
		return true;
	}

}

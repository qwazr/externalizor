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

import org.apache.commons.lang3.RandomUtils;

public class SimplePrimitive extends AutoExternalizor {

	private final int intValue;
	private final short shortValue;
	private final long longValue;
	private final float floatValue;
	private final double doubleValue;
	private final boolean booleanValue;
	private final byte byteValue;
	private final char charValue;

	public SimplePrimitive() {
		intValue = RandomUtils.nextInt();
		shortValue = (short) RandomUtils.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
		longValue = RandomUtils.nextLong();
		floatValue = RandomUtils.nextFloat();
		doubleValue = RandomUtils.nextDouble();
		booleanValue = RandomUtils.nextInt(0, 2) == 0;
		byteValue = (byte) RandomUtils.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
		charValue = (char) RandomUtils.nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SimplePrimitive))
			return false;
		final SimplePrimitive s = (SimplePrimitive) o;
		if (intValue != s.intValue)
			return false;
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
		return true;
	}

}

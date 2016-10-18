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

import java.util.Arrays;

public class SimplePrimitive extends AutoExternalizor {

	private final int intValue;
	public int[] intArray;

	private final short shortValue;
	public short[] shortArray;

	private final long longValue;
	public long[] longArray;

	private final float floatValue;
	public float[] floatArray;

	private final double doubleValue;
	public double[] doubleArray;
	public double[] emptyDoubleArray;

	private final boolean booleanValue;
	public boolean[] booleanArray;

	private final byte byteValue;
	public byte[] byteArray;

	private final char charValue;
	public char[] charArray;

	public SimplePrimitive() {

		intValue = RandomUtils.nextInt();
		intArray = new int[] { RandomUtils.nextInt(), RandomUtils.nextInt(), RandomUtils.nextInt() };

		shortValue = (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
		shortArray = new short[] { (short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE),
				(short) RandomUtils.nextInt(0, Short.MAX_VALUE) };

		longValue = RandomUtils.nextLong();
		longArray = new long[] { RandomUtils.nextLong(), RandomUtils.nextLong(), RandomUtils.nextLong() };

		floatValue = RandomUtils.nextFloat();
		floatArray = new float[] { RandomUtils.nextFloat(), RandomUtils.nextFloat(), RandomUtils.nextFloat() };

		doubleValue = RandomUtils.nextDouble();
		doubleArray = new double[] { RandomUtils.nextDouble(), RandomUtils.nextDouble(), RandomUtils.nextDouble() };
		emptyDoubleArray = new double[0];

		booleanValue = RandomUtils.nextInt(0, 2) == 0;
		booleanArray = new boolean[] { RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1,
				RandomUtils.nextInt(0, 2) == 1 };

		byteValue = (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE);
		byteArray = new byte[] { (byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE),
				(byte) RandomUtils.nextInt(0, Byte.MAX_VALUE) };

		charValue = (char) RandomUtils.nextInt(0, Character.MAX_VALUE);
		charArray = new char[] { (char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE),
				(char) RandomUtils.nextInt(0, Character.MAX_VALUE) };

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SimplePrimitive))
			return false;
		final SimplePrimitive s = (SimplePrimitive) o;
		if (intValue != s.intValue)
			return false;
		if (!Arrays.equals(intArray, s.intArray))
			return false;

		if (shortValue != s.shortValue)
			return false;
		if (!Arrays.equals(shortArray, s.shortArray))
			return false;

		if (longValue != s.longValue)
			return false;
		if (!Arrays.equals(longArray, s.longArray))
			return false;

		if (floatValue != s.floatValue)
			return false;
		if (!Arrays.equals(floatArray, s.floatArray))
			return false;

		if (doubleValue != s.doubleValue)
			return false;
		if (!Arrays.equals(doubleArray, s.doubleArray))
			return false;

		if (booleanValue != s.booleanValue)
			return false;
		if (!Arrays.equals(booleanArray, s.booleanArray))
			return false;

		if (byteValue != s.byteValue)
			return false;
		if (!Arrays.equals(byteArray, s.byteArray))
			return false;

		if (charValue != s.charValue)
			return false;
		if (!Arrays.equals(charArray, s.charArray))
			return false;

		return true;
	}

}

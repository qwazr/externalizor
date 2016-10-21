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

import java.io.*;
import java.time.*;
import java.util.*;

public class SimpleTime implements Serializable {

	private final Calendar calNullValue;
	private final Calendar calValue;
	private final Calendar[] calArray;
	private final ArrayList<Calendar> calList;

	private final Date dateNullValue;
	private final Date dateValue;
	private final Date[] dateArray;
	private final ArrayList<Date> dateList;

	private final Duration durationNullValue;
	private final Duration durationValue;
	private final Duration[] durationArray;
	private final ArrayList<Duration> durationList;

	private final Instant instantNullValue;
	private final Instant instantValue;
	private final Instant[] instantArray;
	private final ArrayList<Instant> instantList;

	private final LocalTime localTimeNullValue;
	private final LocalTime localTimeValue;
	private final LocalTime[] localTimeArray;
	private final ArrayList<LocalTime> localTimeList;

	private final LocalDate localDateNullValue;
	private final LocalDate localDateValue;
	private final LocalDate[] localDateArray;
	private final ArrayList<LocalDate> localDateList;

	private final LocalDateTime localDateTimeNullValue;
	private final LocalDateTime localDateTimeValue;
	private final LocalDateTime[] localDateTimeArray;
	private final ArrayList<LocalDateTime> localDateTimeList;

	private final MonthDay monthDayNullValue;
	private final MonthDay monthDayValue;
	private final MonthDay[] monthDayArray;
	private final ArrayList<MonthDay> monthDayList;

	private final Period periodNullValue;
	private final Period periodValue;
	private final Period[] periodArray;
	private final ArrayList<Period> periodList;

	private final Year yearNullValue;
	private final Year yearValue;
	private final Year[] yearArray;
	private final ArrayList<Year> yearList;

	public SimpleTime() {

		calNullValue = null;
		calValue = Calendar.getInstance();
		calValue.setTimeInMillis(RandomUtils.nextLong());
		calArray = new Calendar[] { calNullValue, calValue };
		calList = new ArrayList(Arrays.asList(calValue, calNullValue));

		dateNullValue = null;
		dateValue = new Date(RandomUtils.nextLong());
		dateArray = new Date[] { dateNullValue, dateValue };
		dateList = new ArrayList(Arrays.asList(dateValue, dateNullValue));

		durationNullValue = null;
		durationValue = Duration.ofSeconds(RandomUtils.nextLong());
		durationArray = new Duration[] { durationNullValue, durationValue };
		durationList = new ArrayList(Arrays.asList(durationValue, durationNullValue));

		instantNullValue = null;
		instantValue = Instant.ofEpochSecond(RandomUtils.nextLong(0, Instant.MAX.getEpochSecond()));
		instantArray = new Instant[] { instantNullValue, instantValue };
		instantList = new ArrayList(Arrays.asList(instantValue, instantNullValue));

		localTimeNullValue = null;
		localTimeValue =
				LocalTime.of(RandomUtils.nextInt(0, 24), RandomUtils.nextInt(0, 60), RandomUtils.nextInt(0, 60));
		localTimeArray = new LocalTime[] { localTimeNullValue, localTimeValue };
		localTimeList = new ArrayList(Arrays.asList(localTimeValue, localTimeNullValue));

		localDateNullValue = null;
		localDateValue =
				LocalDate.of(RandomUtils.nextInt(2000, 3000), RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29));
		localDateArray = new LocalDate[] { localDateNullValue, localDateValue };
		localDateList = new ArrayList(Arrays.asList(localDateValue, localDateNullValue));

		localDateTimeNullValue = null;
		localDateTimeValue = LocalDateTime.of(
				LocalDate.of(RandomUtils.nextInt(2000, 3000), RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29)),
				LocalTime.of(RandomUtils.nextInt(0, 24), RandomUtils.nextInt(0, 60), RandomUtils.nextInt(0, 60)));
		localDateTimeArray = new LocalDateTime[] { localDateTimeNullValue, localDateTimeValue };
		localDateTimeList = new ArrayList(Arrays.asList(localDateTimeValue, localDateTimeNullValue));

		monthDayNullValue = null;
		monthDayValue = MonthDay.of(RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29));
		monthDayArray = new MonthDay[] { monthDayNullValue, monthDayValue };
		monthDayList = new ArrayList(Arrays.asList(monthDayValue, monthDayNullValue));

		periodNullValue = null;
		periodValue = Period.of(RandomUtils.nextInt(0, Year.MAX_VALUE), RandomUtils.nextInt(1, 13),
				RandomUtils.nextInt(1, 29));
		periodArray = new Period[] { periodNullValue, periodValue };
		periodList = new ArrayList(Arrays.asList(periodValue, periodNullValue));

		yearNullValue = null;
		yearValue = Year.of(RandomUtils.nextInt(0, Year.MAX_VALUE));
		yearArray = new Year[] { yearNullValue, yearValue };
		yearList = new ArrayList(Arrays.asList(yearValue, yearNullValue));

	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SimpleTime))
			return false;
		final SimpleTime s = (SimpleTime) o;

		if (!Objects.equals(calNullValue, s.calNullValue))
			return false;
		if (!Objects.equals(calValue, s.calValue))
			return false;
		if (!Arrays.equals(calArray, s.calArray))
			return false;
		if (!Objects.equals(calList, s.calList))
			return false;

		if (!Objects.equals(dateNullValue, s.dateNullValue))
			return false;
		if (!Objects.equals(dateValue, s.dateValue))
			return false;
		if (!Arrays.equals(dateArray, s.dateArray))
			return false;
		if (!Objects.equals(dateList, s.dateList))
			return false;

		if (!Objects.equals(durationNullValue, s.durationNullValue))
			return false;
		if (!Objects.equals(durationValue, s.durationValue))
			return false;
		if (!Arrays.equals(durationArray, s.durationArray))
			return false;
		if (!Objects.equals(durationList, s.durationList))
			return false;

		if (!Objects.equals(instantNullValue, s.instantNullValue))
			return false;
		if (!Objects.equals(instantValue, s.instantValue))
			return false;
		if (!Arrays.equals(instantArray, s.instantArray))
			return false;
		if (!Objects.equals(instantList, s.instantList))
			return false;

		if (!Objects.equals(localTimeNullValue, s.localTimeNullValue))
			return false;
		if (!Objects.equals(localTimeValue, s.localTimeValue))
			return false;
		if (!Arrays.equals(localTimeArray, s.localTimeArray))
			return false;
		if (!Objects.equals(localTimeList, s.localTimeList))
			return false;

		if (!Objects.equals(localDateNullValue, s.localDateNullValue))
			return false;
		if (!Objects.equals(localDateValue, s.localDateValue))
			return false;
		if (!Arrays.equals(localDateArray, s.localDateArray))
			return false;
		if (!Objects.equals(localDateList, s.localDateList))
			return false;

		if (!Objects.equals(localDateTimeNullValue, s.localDateTimeNullValue))
			return false;
		if (!Objects.equals(localDateTimeValue, s.localDateTimeValue))
			return false;
		if (!Arrays.equals(localDateTimeArray, s.localDateTimeArray))
			return false;
		if (!Objects.equals(localDateTimeList, s.localDateTimeList))
			return false;

		if (!Objects.equals(monthDayNullValue, s.monthDayNullValue))
			return false;
		if (!Objects.equals(monthDayValue, s.monthDayValue))
			return false;
		if (!Arrays.equals(monthDayArray, s.monthDayArray))
			return false;
		if (!Objects.equals(monthDayList, s.monthDayList))
			return false;

		if (!Objects.equals(periodNullValue, s.periodNullValue))
			return false;
		if (!Objects.equals(periodValue, s.periodValue))
			return false;
		if (!Arrays.equals(periodArray, s.periodArray))
			return false;
		if (!Objects.equals(periodList, s.periodList))
			return false;

		if (!Objects.equals(yearNullValue, s.yearNullValue))
			return false;
		if (!Objects.equals(yearValue, s.yearValue))
			return false;
		if (!Arrays.equals(yearArray, s.yearArray))
			return false;
		if (!Objects.equals(yearList, s.yearList))
			return false;

		return true;
	}

	public static class External extends SimpleTime implements Externalizable {

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

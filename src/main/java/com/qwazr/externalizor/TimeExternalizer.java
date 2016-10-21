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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

/**
 * Handle basic java.time classes:
 * Date, Calendar, Duration, Instant, LocalDate, LocalDateTime, LocalTime, MonthDay, Period, Year
 *
 * @param <T>
 */
interface TimeExternalizer<T, V> extends Externalizer<T, V> {

	static <T, V> Externalizer<T, V> time(final Class<? extends T> clazz) {
		if (Calendar.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) CalendarExternalizer.INSTANCE;
		if (Date.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) DateExternalizer.INSTANCE;
		if (Duration.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) DurationExternalizer.INSTANCE;
		if (Instant.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) InstantExternalizer.INSTANCE;
		if (LocalDate.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) LocalDateExternalizer.INSTANCE;
		if (LocalDateTime.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) LocalDateTimeExternalizer.INSTANCE;
		if (LocalTime.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) LocalTimeExternalizer.INSTANCE;
		if (MonthDay.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) MonthDayExternalizer.INSTANCE;
		if (Period.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) PeriodExternalizer.INSTANCE;
		if (Year.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) YearExternalizer.INSTANCE;
		// Can't handle this class, we return null
		return null;
	}

	static <T, V> Externalizer<T, V> time(final Field field, final Class<? extends T> clazz) {
		if (Calendar.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer<>(field,
					CalendarExternalizer.INSTANCE);
		if (Date.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field, DateExternalizer.INSTANCE);
		if (Duration.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					DurationExternalizer.INSTANCE);
		if (Instant.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					InstantExternalizer.INSTANCE);
		if (LocalDate.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					LocalDateExternalizer.INSTANCE);
		if (LocalDateTime.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					LocalDateTimeExternalizer.INSTANCE);
		if (LocalTime.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					LocalTimeExternalizer.INSTANCE);
		if (MonthDay.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					MonthDayExternalizer.INSTANCE);
		if (Period.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field,
					PeriodExternalizer.INSTANCE);
		if (Year.class.isAssignableFrom(clazz))
			return (Externalizer<T, V>) new FieldExternalizer.FieldParentExternalizer(field, YearExternalizer.INSTANCE);
		// Can't handle this class, we return null
		return null;
	}

	@Override
	default void readExternal(final T object, final ObjectInput in) throws IOException, ReflectiveOperationException {
		throw new ExternalizorException("Not available");
	}

	final class DateExternalizer implements TimeExternalizer<Date, Date> {

		final static DateExternalizer INSTANCE = new DateExternalizer();

		@Override
		final public void writeExternal(final Date object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.getTime());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Date readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? new Date(in.readLong()) : null;
		}
	}

	final class CalendarExternalizer implements TimeExternalizer<Calendar, Calendar> {

		final static CalendarExternalizer INSTANCE = new CalendarExternalizer();

		@Override
		final public void writeExternal(final Calendar object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.getTimeInMillis());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Calendar readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			if (!in.readBoolean())
				return null;
			final Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(in.readLong());
			return cal;
		}
	}

	final class DurationExternalizer implements TimeExternalizer<Duration, Duration> {

		final static DurationExternalizer INSTANCE = new DurationExternalizer();

		@Override
		final public void writeExternal(final Duration object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.getSeconds());
				out.writeInt(object.getNano());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Duration readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? Duration.ofSeconds(in.readLong(), in.readInt()) : null;
		}
	}

	final class InstantExternalizer implements TimeExternalizer<Instant, Instant> {

		final static InstantExternalizer INSTANCE = new InstantExternalizer();

		@Override
		final public void writeExternal(final Instant object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.getEpochSecond());
				out.writeInt(object.getNano());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Instant readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? Instant.ofEpochSecond(in.readLong(), in.readInt()) : null;
		}
	}

	final class LocalDateExternalizer implements TimeExternalizer<LocalDate, LocalDate> {

		final static LocalDateExternalizer INSTANCE = new LocalDateExternalizer();

		@Override
		final public void writeExternal(final LocalDate object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.getLong(ChronoField.EPOCH_DAY));
			} else
				out.writeBoolean(false);
		}

		@Override
		final public LocalDate readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? LocalDate.ofEpochDay(in.readLong()) : null;
		}
	}

	final class LocalDateTimeExternalizer implements TimeExternalizer<LocalDateTime, LocalDateTime> {

		final static LocalDateTimeExternalizer INSTANCE = new LocalDateTimeExternalizer();

		@Override
		final public void writeExternal(final LocalDateTime object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.toLocalDate().getLong(ChronoField.EPOCH_DAY));
				out.writeLong(object.toLocalTime().toNanoOfDay());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public LocalDateTime readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ?
					LocalDateTime.of(LocalDate.ofEpochDay(in.readLong()), LocalTime.ofNanoOfDay(in.readLong())) :
					null;
		}
	}

	final class LocalTimeExternalizer implements TimeExternalizer<LocalTime, LocalTime> {

		final static LocalTimeExternalizer INSTANCE = new LocalTimeExternalizer();

		@Override
		final public void writeExternal(final LocalTime object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeLong(object.toNanoOfDay());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public LocalTime readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? LocalTime.ofNanoOfDay(in.readLong()) : null;
		}
	}

	final class MonthDayExternalizer implements TimeExternalizer<MonthDay, MonthDay> {

		final static MonthDayExternalizer INSTANCE = new MonthDayExternalizer();

		@Override
		final public void writeExternal(final MonthDay object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeByte(object.getMonthValue());
				out.writeByte(object.getDayOfMonth());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public MonthDay readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? MonthDay.of(in.readByte(), in.readByte()) : null;
		}
	}

	final class PeriodExternalizer implements TimeExternalizer<Period, Period> {

		final static PeriodExternalizer INSTANCE = new PeriodExternalizer();

		@Override
		final public void writeExternal(final Period object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeInt(object.getYears());
				out.writeInt(object.getMonths());
				out.writeInt(object.getDays());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Period readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? Period.of(in.readInt(), in.readInt(), in.readInt()) : null;
		}
	}

	final class YearExternalizer implements TimeExternalizer<Year, Year> {

		final static YearExternalizer INSTANCE = new YearExternalizer();

		@Override
		final public void writeExternal(final Year object, final ObjectOutput out) throws IOException {
			if (object != null) {
				out.writeBoolean(true);
				out.writeInt(object.getValue());
			} else
				out.writeBoolean(false);
		}

		@Override
		final public Year readObject(final ObjectInput in) throws IOException, ClassNotFoundException {
			return in.readBoolean() ? Year.of(in.readInt()) : null;
		}
	}

}

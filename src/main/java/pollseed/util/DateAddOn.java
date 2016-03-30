package pollseed.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.joda.time.DateTime;

public class DateAddOn implements Serializable {
    private static final long serialVersionUID = 1674067964754938905L;

    private DateAddOn() {
    }

    /**
     * now date of "Date" type
     * 
     * @return now date
     */
    public static Date now() {
        return toDate(nowZonedDateTime());
    }

    /**
     * now date of "DateTime" type
     * 
     * @return now date
     */
    public static DateTime nowDateTime() {
        return toDateTime(nowZonedDateTime());
    }

    /**
     * now date of "ZonedDateTime" type
     * 
     * @return now date
     */
    public static ZonedDateTime nowZonedDateTime() {
        return ZonedDateTime.now();
    }

    /**
     * Parse from "Date" type to "ZonedDateTime" type. If "Date" is NULL, use
     * {@link DateAddOn#now()}.
     * 
     * @param date
     *            "Date" type
     * @return "ZonedDateTime" type
     */
    public static ZonedDateTime toZonedDateTime(final Date date) {
        return toOptionalOrElseNow(date).toInstant().atZone(ZoneId.systemDefault());
    }

    private static Date toOptionalOrElseNow(final Date date) {
        return toOptionalOrElse(date, now());
    }

    private static <T> T toOptionalOrElse(final T t, final T orElse) {
        return toOptional(t).orElse(orElse);
    }

    private static <T> Optional<T> toOptional(final T t) {
        return Optional.ofNullable(t);
    }

    /**
     * Parse from "DateTime" type to "ZonedDateTime" type.
     * 
     * @param dateTime
     *            "DateTime" type
     * @return "ZonedDateTime" type
     */
    public static ZonedDateTime toZonedDateTime(final DateTime dateTime) {
        return toOptionalOrElseNow(dateTime.toDate()).toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * Parse from "ZonedDateTime" type to "Date" type.
     * 
     * @param zonedDateTime
     *            "ZonedDateTime" type
     * @return "Date" type
     */
    public static Date toDate(final ZonedDateTime zonedDateTime) {
        return Date.from(toOptionalOrElseNow(zonedDateTime).toInstant());
    }

    private static ZonedDateTime toOptionalOrElseNow(final ZonedDateTime zonedDateTime) {
        return Optional.ofNullable(zonedDateTime).orElse(nowZonedDateTime());
    }

    /**
     * Parse from "ZonedDateTime" type to "DateTime" type.
     * 
     * @param zonedDateTime
     *            "ZonedDateTime" type
     * @return "DateTime" type
     */
    public static DateTime toDateTime(final ZonedDateTime zonedDateTime) {
        return new DateTime(toDate(zonedDateTime));
    }

    /**
     * Format from "Date" type to "String" type by {@link DateFormat}.
     * 
     * @param date
     *            "Date" type
     * @param dateFormat
     *            {@link DateFormat} return type
     * @return parsed {@link DateFormat}
     */
    public static String format(final Date date, final DateFormat dateFormat) {
        return format(toZonedDateTime(date), dateFormat);
    }

    /**
     * Format from "DateTime" type to "String" type by {@link DateFormat}.
     * 
     * @param dateTime
     *            "DateTime" type
     * @param dateFormat
     *            {@link DateFormat} return type
     * @return parsed {@link DateFormat}
     */
    public static String format(final DateTime dateTime, final DateFormat dateFormat) {
        return format(toZonedDateTime(dateTime), dateFormat);
    }

    /**
     * Format from "ZonedDateTime" type to "String" type by {@link DateFormat}.
     * 
     * @param zonedDateTime
     *            "ZonedDateTime" type
     * @param dateFormat
     *            {@link DateFormat} return type
     * @return parsed {@link DateFormat}
     */
    public static String format(final ZonedDateTime zonedDateTime, final DateFormat dateFormat) {
        return toOptionalOrElseNow(zonedDateTime).format(toDateTimeFormatter(dateFormat));
    }

    private static DateTimeFormatter toDateTimeFormatter(final DateFormat dateFormat) {
        return DateTimeFormatter.ofPattern(dateFormat.value);
    }

    /**
     * Parse from "String" type to "LocalDate" type.
     * 
     * @param str
     *            specific date string
     * @param dateFormat
     *            "DateFormat" of specific date string
     * @return "LocalDate" type
     */
    public static LocalDate format(final String str, final DateFormat dateFormat) {
        return toOptional(str).map(e -> LocalDate.parse(e, toDateTimeFormatter(dateFormat))).orElse(
                null);
    }

    /**
     * Format for parsing date.
     */
    public enum DateFormat {
        SLASH("yyyy/MM/dd"),
        SLASH_HMS("yyyy/MM/dd HH:mm:ss"),
        HYPHEN("yyyy-MM-dd"),
        HYPHEN_HMS("yyyy-MM-dd HH:mm:ss"),
        SPACE("yyyy MM dd"),
        SPACE_HMS("yyyy MM dd HH:mm:ss"), ;
        String value;

        DateFormat(final String value) {
            this.value = value;
        }
    }
}

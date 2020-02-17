package com.cqjtu.csi.utils;

import java.time.*;
import java.time.temporal.TemporalField;
import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("Asia/Shanghai"));

    }

    public static Date nowDate() {
        return Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    }

    public static boolean after(long date) {
        return compare(now().toEpochDay(), date);
    }

    public static boolean before(long date) {
        return compare(date, now().toEpochDay());
    }

    public static boolean compare(long date1, long date2) {
        return date1 <= date2;
    }
}

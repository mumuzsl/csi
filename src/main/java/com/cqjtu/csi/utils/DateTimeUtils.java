package com.cqjtu.csi.utils;

import java.time.*;
import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static Long now() {
        return System.currentTimeMillis();

    }

    public static Date nowDate() {
        return Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    }

    public static boolean after(long date) {
        return compare(now(), date);
    }

    public static boolean before(long date) {
        return compare(date, now());
    }

    public static boolean compare(long date1, long date2) {
        return date1 <= date2;
    }
}

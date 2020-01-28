package com.cqjtu.csi.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static Date now() {
        return Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    }

    public static boolean after(long date) {
        return compare(now().getTime(), date);
    }

    public static boolean before(long date) {
        return compare(date, now().getTime());
    }

    public static boolean compare(long date1, long date2) {
        return date1 <= date2;
    }
}

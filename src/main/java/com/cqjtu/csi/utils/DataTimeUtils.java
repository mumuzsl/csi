package com.cqjtu.csi.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class DataTimeUtils {

    private DataTimeUtils() {

    }

    public static Date now() {
        return Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    }

}

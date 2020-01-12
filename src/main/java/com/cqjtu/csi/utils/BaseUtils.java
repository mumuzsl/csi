package com.cqjtu.csi.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.UUID;

public class BaseUtils {

    /**
     * Gets random uuid without dash.
     *
     * @return random uuid without dash
     */
    @NonNull
    public static String randomUUIDWithoutDash() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }

}

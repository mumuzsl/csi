package com.cqjtu.csi.utils;

import com.cqjtu.csi.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

/**
 * @author mumu
 * @date 2020/1/20
 */
public class PageUtils {

    private PageUtils() {
    }

    public static final Integer DEFAULT_SIZE = 10;

    public static PageRequest of(String page) {
        return of(safe(Integer.parseInt(page)), DEFAULT_SIZE);
    }

    public static PageRequest of(Integer page) {
        return of(safe(page), DEFAULT_SIZE);
    }

    public static PageRequest of(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    public static Integer safe(Integer page) {
        if (page < 0)
            throw new BadRequestException("页码小于0");
        return page;
    }

    public static Integer safe(Integer page, Integer total) {
        if (safe(page) > total)
            throw new BadRequestException("页码大于总页数");
        return page;
    }
}

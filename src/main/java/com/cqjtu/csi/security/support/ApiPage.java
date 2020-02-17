package com.cqjtu.csi.security.support;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author mumu
 * @date 2020/2/15
 */
public class ApiPage extends PageRequest implements Pageable {

    public ApiPage(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    @Override
    public int getPageNumber() {
        int page = super.getPageNumber();
        return page >= 1 ? page - 1 : 0;
    }
}

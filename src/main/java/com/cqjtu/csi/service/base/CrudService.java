package com.cqjtu.csi.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 * @see AbstractCrudService
 */
public interface CrudService<DOMAIN, ID> {

    List<DOMAIN> listAll();

    Page<DOMAIN> pageBy(Integer page);

    Page<DOMAIN> pageBy(String page);

    Page<DOMAIN> pageBy(Pageable pageable);

    DOMAIN getById(ID id);

    Long Count();

    Page search(DOMAIN keyword, Pageable pageable);
}

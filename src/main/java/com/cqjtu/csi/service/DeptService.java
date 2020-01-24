package com.cqjtu.csi.service;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author mumu
 * @date 2020/1/20
 * implements by
 * @see com.cqjtu.csi.service.impl.DeptServiceImpl
 */
public interface DeptService extends CrudService<Dept, Integer> {

    Page search(String keyword, Pageable pageable);

}

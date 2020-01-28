package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.repository.DeptRepository;
import com.cqjtu.csi.service.DeptService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class DeptServiceImpl extends AbstractCrudService<Dept, Integer> implements DeptService {
    private final DeptRepository deptRepository;

    public DeptServiceImpl(DeptRepository repository) {
        super(repository);
        this.deptRepository = repository;
    }

    @Override
    public Page<Dept> search(String keyword, Pageable pageable) {
        return deptRepository.findByNameContaining(keyword, pageable);
    }

}

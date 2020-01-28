package com.cqjtu.csi.service.impl;

import com.alibaba.fastjson.JSON;
import com.cqjtu.csi.model.dto.EmployeeDTO;
import com.cqjtu.csi.model.entity.Employee;
import com.cqjtu.csi.repository.EmployeeRepository;
import com.cqjtu.csi.service.EmployeeService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class EmployeeServiceImpl extends AbstractCrudService<Employee, Integer> implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
        this.employeeRepository = repository;
    }

    @Override
    public Page<Employee> search(String keyword, Pageable pageable) {
        return search(JSON.parseObject(keyword, Employee.class), pageable);
    }
}

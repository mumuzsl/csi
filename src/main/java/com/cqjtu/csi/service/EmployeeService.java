package com.cqjtu.csi.service;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.model.entity.Employee;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author mumu
 * @date 2020/1/20
 */
public interface EmployeeService extends CrudService<Employee, Integer> {

    Page search(String json, Pageable pageable);

}

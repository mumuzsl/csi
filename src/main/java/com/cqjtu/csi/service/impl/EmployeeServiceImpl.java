package com.cqjtu.csi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.model.dto.EmployeeDTO;
import com.cqjtu.csi.model.entity.Employee;
import com.cqjtu.csi.repository.DeptRepository;
import com.cqjtu.csi.repository.EmployeeRepository;
import com.cqjtu.csi.repository.JobRepository;
import com.cqjtu.csi.repository.base.BaseRepository;
import com.cqjtu.csi.service.DeptService;
import com.cqjtu.csi.service.EmployeeService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class EmployeeServiceImpl extends AbstractCrudService<Employee, Integer> implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DeptRepository deptRepository;
    private final JobRepository jobRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DeptRepository deptRepository,
                               JobRepository jobRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
        this.deptRepository = deptRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public Page<EmployeeDTO> searchToDto(Employee key, Pageable pageable) {
//        GenericPropertyMatcher contains = GenericPropertyMatchers.contains();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", contains)
                .withMatcher("card_id", contains)
                .withMatcher("phone", contains);

        Page<Employee> search = employeeRepository.findAll(Example.of(key, matcher), pageable);

        return search.map(this::convert);
    }

    @Override
    public EmployeeDTO convertById(Integer id) {
        return convert(getOne(id));
    }

    @Override
    public EmployeeDTO convert(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO().convertFrom(employee);
        Optional.ofNullable(employee.getJobId())
                .map(jobRepository::getName)
                .ifPresent(dto::setJobName);
        Optional.ofNullable(employee.getDeptId())
                .map(deptRepository::getName)
                .ifPresent(dto::setDeptName);
        return dto;
    }

    @Override
    public Page search(String keyword, Pageable pageable) {
//        return search(JSON.parseObject(keyword, Employee.class), pageable);
//        Employee employee = JSON.parseObject(keyword, Employee.class);
//        return employeeRepository.search(
//                employee.getName(),
//                employee.getPhone(),
//                employee.getCardId(),
//                employee.getSex(),
//                employee.getJobId(),
//                employee.getDeptId(),
//                PageUtils.of(pageable));
        return null;
    }

    @Override
    public Page pageBy(Pageable pageable) {
        return employeeRepository.fill(pageable);
    }
}

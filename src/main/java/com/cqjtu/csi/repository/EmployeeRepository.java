package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Employee;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface EmployeeRepository extends BaseRepository<Employee,Integer> {
    /**
     *查询所有的员工
     * @return List
     */
    List<Employee> findAll();

    /**
     * 通过编号查询
     * @param id
     * @return Employee
     */
    @NonNull
    Optional<Employee> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     * @param name
     * @return Employee
     */
    @NonNull
    Optional<Employee> findByName(@NonNull String name);

    /**
     * 添加员工信息
     * @param employee
     *
     */
    void addEmployee(Employee employee);

    /**
     * 修改员工信息
     * @param employee
     *
     */
    void updateEmployee(Employee employee);

    /**
     * 删除部门信息
     * @param id
     *
     */
    void removeEmployee(Integer id);
}

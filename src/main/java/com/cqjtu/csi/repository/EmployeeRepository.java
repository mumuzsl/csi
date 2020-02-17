package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.dto.EmployeeDTO;
import com.cqjtu.csi.model.entity.Employee;
import com.cqjtu.csi.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
    /**
     * 查询所有的员工
     *
     * @return List
     */
    @Override
    List<Employee> findAll();

    /**
     * 通过编号查询
     *
     * @param id
     * @return Employee
     */
    @Override
    @NonNull
    Optional<Employee> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     *
     * @param name
     * @return Employee
     */
    @NonNull
    Optional<Employee> findByName(@NonNull String name);

    @Query(value = "select dept.name as dept_name, job.name as job_name, `employee`.* " +
            "from `employee`,`dept`,`job` " +
            "where `employee`.dept_id=`dept`.id and `employee`.job_id=`job`.id", nativeQuery = true)
    Page<Map> fill(Pageable pageable);

    @Query(value = "select dept.name as dept_name, job.name as job_name, `employee`.* " +
            "from `employee`,`dept`,`job` " +
            "where `employee`.dept_id=`dept`.id " +
            "and `employee`.job_id=`job`.id " +
            "and `employee`.`name` like concat('%',:name,'%')" +
            "and `employee`.`phone` like concat('%', :phone,'%')" +
            "and `employee`.`card_id` like concat('%',:cardId,'%')" +
            "and `employee`.`sex` = :sex " +
            "and `employee`.job_id = :jobId " +
            "and employee.dept_id = :deptId ", nativeQuery = true)
    Page<EmployeeDTO> search(@Param("name") String name,
                             @Param("phone") String phone,
                             @Param("cardId") String cardId,
                             @Param("sex") String sex,
                             @Param("jobId") int jobId,
                             @Param("deptId") int deptId,
                             Pageable pageable);
}

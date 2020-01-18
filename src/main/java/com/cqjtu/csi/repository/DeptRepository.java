package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Dept;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface DeptRepository extends BaseRepository<Dept,Integer>{

    /**
     *查询所有的部门的信息
     * @return List
     */
    List<Dept> findAll();

    /**
     * 通过编号查询
     * @param id
     * @return Dept
     */
    @NonNull
    Optional<Dept> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     * @param name
     * @return Dept
     */
    @NonNull
    Optional<Dept> findByName(@NonNull String name);

    /**
     * 添加部门信息
     * @param dept
     *
     */
    void addDept(Dept dept);

    /**
     * 修改部门信息
     * @param dept
     *
     */
    void updateDept(Dept dept);

    /**
     * 删除部门信息
     * @param id
     *
     */
    void removeDept(Integer id);
}

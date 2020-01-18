package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Job;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface JobRepository extends BaseRepository<Job,Integer>{

    /**
     *查询所有的职位名称
     * @return List
     */
    List<Job> findAll();

    /**
     * 通过编号查询
     * @param id
     * @return Job
     */
    @NonNull
    Optional<Job> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     * @param name
     * @return Job
     */
    @NonNull
    Optional<Job> findByName(@NonNull String name);

    /**
     * 添加职位信息
     * @param job
     *
     */
    void addJob(Job job);

    /**
     * 修改职位信息
     * @param job
     *
     */
    void updateJob(Job job);

    /**
     * 删除职位信息
     * @param id
     *
     */
    void removeJob(Integer id);

}

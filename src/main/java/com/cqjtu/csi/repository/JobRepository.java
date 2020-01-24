package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Job;
import com.cqjtu.csi.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface JobRepository extends BaseRepository<Job, Integer> {

    /**
     * 查询所有的职位名称
     *
     * @return List
     */
    List<Job> findAll();

    /**
     * 通过编号查询
     *
     * @param id
     * @return Job
     */
    @NonNull
    Optional<Job> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     *
     * @param name
     * @return Job
     */
    @NonNull
    Optional<Job> findByName(@NonNull String name);

    @Query(value = "select * from dept where job.name like concat('%',:name,'%') limit :first, :size", nativeQuery = true)
    List search(@Param("name") String name, @Param("first") Long first, @Param("size") Integer size);

    @Query(value = "select count(*) from dept where job.name like concat('%',:name,'%')", nativeQuery = true)
    Long countByName(@Param("name") String name);
}

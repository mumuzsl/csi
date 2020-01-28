package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.repository.base.BaseRepository;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface DeptRepository extends BaseRepository<Dept, Integer> {


    /**
     * 通过编号查询
     *
     * @param id
     * @return Dept
     */
    @Override
    @NonNull
    Optional<Dept> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     *
     * @param name
     * @return Dept
     */
    @NonNull
    Page<Dept> findByNameContaining(@NonNull String name, @NonNull Pageable pageable);

    @Query(value = "select * from dept where dept.name like concat('%',:name,'%') limit :first, :size", nativeQuery = true)
    List search(@Param("name") String name, @Param("first") Long first, @Param("size") Integer size);

    @Query(value = "select count(*) from dept where dept.name like concat('%',:name,'%')", nativeQuery = true)
    Long countByName(@Param("name") String name);

}

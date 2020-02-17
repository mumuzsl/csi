package com.cqjtu.csi.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @author mumu
 * @date 2020/1/11
 */
@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID>, JpaSpecificationExecutor<DOMAIN> {

    long deleteByIdIn(Collection<ID> ids);


    @Query(value = "select name from #{#entityName} t where t.id = :id", nativeQuery = true)
    String getName(@Param("id") Integer id);
}

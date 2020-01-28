package com.cqjtu.csi.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

/**
 * @author mumu
 * @date 2020/1/11
 */
@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID> {

    long deleteByIdIn(Collection<ID> ids);

}

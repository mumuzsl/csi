package com.cqjtu.csi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID> {
}

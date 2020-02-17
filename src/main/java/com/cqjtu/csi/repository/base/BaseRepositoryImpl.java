package com.cqjtu.csi.repository.base;

import com.cqjtu.csi.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/1/16
 */
public class BaseRepositoryImpl<DOMAIN, ID> extends SimpleJpaRepository<DOMAIN, ID> implements BaseRepository<DOMAIN, ID> {

    private final JpaEntityInformation<DOMAIN, ID> entityInformation;
    private final EntityManager entityManager;

    // 这个地方IDEA可能报错，不影响程序运行，可以不用管它
    // 具体原因还未找到
    // idea error: could not autowire.no beans of 'jpaentityinformation<domain,id>' type found
    public BaseRepositoryImpl(JpaEntityInformation<DOMAIN, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    protected <S extends DOMAIN> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable, Specification<S> spec) {
        return super.readPage(query, domainClass, pageable, spec);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteByIdIn(Collection<ID> ids) {

        List<DOMAIN> entities = findAllById(ids);

        deleteInBatch(entities);

        return entities.size();
    }

    //    @Override
    public Page search(Specification<DOMAIN> specification, Pageable pageable) {
        return findAll(specification, pageable);
    }

    @Override
    public String getName(Integer id) {
        return null;
    }
}

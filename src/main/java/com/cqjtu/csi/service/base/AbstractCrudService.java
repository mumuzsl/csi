package com.cqjtu.csi.service.base;

import cn.hutool.core.util.PageUtil;
import com.alibaba.druid.sql.PagerUtils;
import com.cqjtu.csi.repository.base.BaseRepository;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author mumu
 * @date 2020/1/12
 */
public abstract class AbstractCrudService<DOMAIN, ID> implements CrudService<DOMAIN, ID> {

    private final String domainName;

    private final BaseRepository<DOMAIN, ID> repository;

    protected AbstractCrudService(BaseRepository<DOMAIN, ID> repository) {
        this.repository = repository;

        // Get domain name
        @SuppressWarnings("unchecked")
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType(0);
        domainName = domainClass.getSimpleName();
    }

    /**
     * Gets actual generic type.
     *
     * @param index generic type index
     * @return real generic type will be returned
     */
    private Type fetchType(int index) {
        Assert.isTrue(index >= 0 && index <= 1, "type index must be between 0 to 1");

        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public DOMAIN insert(DOMAIN domain) {
        return repository.save(domain);
    }

    public DOMAIN update(DOMAIN domain) {
        return repository.saveAndFlush(domain);
    }

    public void delete(DOMAIN domain) {
        repository.delete(domain);
    }

    @Override
    public Page<DOMAIN> pageBy(Integer page) {
        return repository.findAll(PageUtils.of(page));
    }

    @Override
    public Page<DOMAIN> pageBy(String page) {
        return pageBy(PageUtils.of(page));
    }

    @Override
    public Page<DOMAIN> pageBy(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public DOMAIN getById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long Count() {
        return repository.count();
    }

    @Override
    public Page search(DOMAIN domain, Pageable pageable) {
        Example<DOMAIN> example = Example.of(domain);
        return repository.findAll(example, pageable);
    }

    @Override
    public List<DOMAIN> listAll() {
        return repository.findAll();
    }
}

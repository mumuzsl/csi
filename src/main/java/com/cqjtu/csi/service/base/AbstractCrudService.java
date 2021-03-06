package com.cqjtu.csi.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.BeanUtilsException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.repository.base.BaseRepository;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.BeanUtils;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author mumu
 * @date 2020/1/12
 */
public abstract class AbstractCrudService<DOMAIN, ID> implements CrudService<DOMAIN, ID> {

    private final String domainName;
    private final Class<?> domainClass;
    private final BaseRepository<DOMAIN, ID> repository;
    protected final ExampleMatcher.GenericPropertyMatcher contains = ExampleMatcher.GenericPropertyMatchers.contains();

    protected AbstractCrudService(BaseRepository<DOMAIN, ID> repository) {
        this.repository = repository;
        this.domainClass = (Class<DOMAIN>) fetchType(0);
        this.domainName = domainClass.getSimpleName();
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

//    @Override
//    public DOMAIN insertByJson(String json) {
//        return insert((DOMAIN) JSON.parseObject(json, domainClass));
//    }
//
//    @Override
//    public DOMAIN updateByJson(String json) {
//        return update((DOMAIN) JSON.parseObject(json, domainClass));
//    }

    @Override
    public void removeInBetch(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        repository.deleteByIdIn(ids);
    }

    @Override
    @Transactional
    public void removeById(ID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public DOMAIN insert(DOMAIN domain) {
        Assert.notNull(domain, "domain not be null");
        return repository.save(domain);
    }

    @Override
    @Transactional
    public DOMAIN updateById(ID id, DOMAIN domain) {
        Assert.notNull(id, "id not be null");

        DOMAIN oldDomain = this.getOne(id);

        BeanUtils.updateProperties(domain, oldDomain);

        return repository.saveAndFlush(oldDomain);
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, "domain not be null");
        try {
            Object id = BeanUtils.getFieldValue(domain, "id");
            return updateById((ID) Optional.of(id).get(), domain);
        } catch (BeanUtilsException | NullPointerException e) {
            throw new BadRequestException("没有id");
        }
    }

    @Override
    @Transactional
    public DOMAIN updateAll(DOMAIN domain) {
        return repository.saveAndFlush(domain);
    }

    @Override
    @Transactional
    public void remove(DOMAIN domain) {
        repository.delete(domain);
    }

    @Override
    public Page pageBy(Integer page) {
        return repository.findAll(PageUtils.of(page));
    }

    @Override
    public Page pageBy(String page) {
        return pageBy(PageUtils.of(page));
    }

    @Override
    public Page pageBy(Pageable pageable) {
        return repository.findAll(PageUtils.of(pageable));
    }

    @Override
    public Optional<DOMAIN> getOneById(ID id) {
        return repository.findById(id);
    }

    @Override
    public DOMAIN getOne(ID id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("无效id"));
    }

    @Override
    public Optional<DOMAIN> getById(ID id) {
        return Optional.of(getOne(id));
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List listAll() {
        return repository.findAll();
    }

    @Override
    public Page search(String keyword, Pageable pageable) {
        return search(BaseUtils.oneKeyValueMap(keyword, "name"), pageable);
    }

    @Override
    public Page search(Map map, Pageable pageable) {
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType(0);
        Object key = BeanUtil.mapToBean(map, domainClass, true);
        return search((DOMAIN) key, pageable);
    }

    @Override
    public Page search(DOMAIN key, Pageable pageable) {
        return repository.findAll(Example.of(key), pageable);
    }
}

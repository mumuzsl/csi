package com.cqjtu.csi.service.base;

import com.cqjtu.csi.repository.BaseRepository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author mumu
 * @date 2020/1/12
 */
public abstract class CrudService<DOMAIN, ID> {

    private final String domainName;

    private final BaseRepository<DOMAIN, ID> repository;

    protected CrudService(BaseRepository<DOMAIN, ID> repository) {
        this.repository = repository;

        // Get domain name
        @SuppressWarnings("unchecked")
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType(0);
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

    public DOMAIN insert(DOMAIN domain) {
        return repository.save(domain);
    }

    public DOMAIN update(DOMAIN domain) {
        return repository.saveAndFlush(domain);
    }

    public void delete(DOMAIN domain){
        repository.delete(domain);
    }


}

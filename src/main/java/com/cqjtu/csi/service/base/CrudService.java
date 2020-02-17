package com.cqjtu.csi.service.base;

import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.sun.java.browser.plugin2.DOM;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/20
 * @see AbstractCrudService
 */
public interface CrudService<DOMAIN, ID> {

    DOMAIN insert(DOMAIN domain);

    DOMAIN updateById(ID id, DOMAIN domain);

    DOMAIN update(DOMAIN domain);

    DOMAIN updateAll(DOMAIN domain);

    void removeInBetch(Collection<ID> ids);

    void removeById(ID id);

    void remove(DOMAIN domain);

    List listAll();

    Page pageBy(Integer page);

    Page pageBy(String page);

    Page pageBy(Pageable pageable);

    DOMAIN getOne(ID id);

    Optional<DOMAIN> getById(ID id);

    Long count();

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param keyword  关键字
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page search(String keyword, Pageable pageable);

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param map      关键字Map，key是要搜索的列，value是要搜索的值
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page search(Map map, Pageable pageable);

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param key      传入对属性设置了关键字的对象
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page search(DOMAIN key, Pageable pageable);
}

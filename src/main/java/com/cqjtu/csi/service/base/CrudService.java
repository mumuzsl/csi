package com.cqjtu.csi.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/1/20
 * @see AbstractCrudService
 */
public interface CrudService<DOMAIN, ID> {

    DOMAIN insert(DOMAIN domain);

    DOMAIN update(DOMAIN domain);

    void removeInBetch(Collection<ID> ids);

    void removeById(ID id);

    void remove(DOMAIN domain);

    List<DOMAIN> listAll();

    Page<DOMAIN> pageBy(Integer page);

    Page<DOMAIN> pageBy(String page);

    Page<DOMAIN> pageBy(Pageable pageable);

    DOMAIN getById(ID id);

    Long count();

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param keyword  关键字
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page<DOMAIN> search(String keyword, Pageable pageable);

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param map      关键字Map，key是要搜索的列，value是要搜索的值
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page<DOMAIN> search(Map map, Pageable pageable);

    /**
     * 在数据中进行搜索
     * 注意：该默认方法不支持模糊搜索
     *
     * @param key      传入对属性设置了关键字的对象
     * @param pageable 分页
     * @return 分页后的搜索结果
     */
    Page<DOMAIN> search(DOMAIN key, Pageable pageable);
}

package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Notice;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface NoticeRepository extends BaseRepository<Notice,Integer> {
    /**
     *查询所有的公告名称
     * @return List
     */
    List<Notice> findAll();

    /**
     * 通过编号查询
     * @param id
     * @return Job
     */
    @NonNull
    Optional<Notice> findById(@NonNull Integer id);

    /**
     * 通过名称查询
     * @param name
     * @return Job
     */
    @NonNull
    Optional<Notice> findByName(@NonNull String name);

    /**
     * 添加公告信息
     * @param notice
     *
     */
    void addNotice(Notice notice);

    /**
     * 修改公告信息
     * @param notice
     *
     */
    void updateNotice(Notice notice);

    /**
     * 删除公告信息
     * @param id
     *
     */
    void removeNotice(Integer id);
}

package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Document;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface DocumentRepository extends BaseRepository<Document,Integer> {
    /**
     *查询所有的下载信息
     * @return List
     */
    List<Document> findAll();

    /**
     * 通过编号查询
     * @param id
     * @return Document
     */
    @NonNull
    Optional<Document> findById(@NonNull Integer id);

    /**
     * 通过标题查询
     * @param title
     * @return Document
     */
    @NonNull
    Optional<Document> findByName(@NonNull String title);

    /**
     * 添加下载中心信息
     * @param document
     *
     */
    void addDocument(Document document);

    /**
     * 修改下载中心信息
     * @param document
     *
     */
    void updateDocument(Document document);

    /**
     * 删除下载中心信息
     * @param id
     *
     */
    void removeDocument(Integer id);
}

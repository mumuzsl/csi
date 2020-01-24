package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.repository.base.BaseRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ly
 * @date 2020/1/18
 */

public interface DocumentRepository extends BaseRepository<Document, Integer> {
    /**
     * 查询所有的下载信息
     *
     * @return List
     */
    List<Document> findAll();

    /**
     * 通过编号查询
     *
     * @param id
     * @return Document
     */
    @NonNull
    Optional<Document> findById(@NonNull Integer id);

    /**
     * 通过标题查询
     *
     * @param title
     * @return Document
     */
    @NonNull
    Optional<Document> findByFilename(@NonNull String title);
}

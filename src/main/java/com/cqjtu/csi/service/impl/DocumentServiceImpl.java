package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.repository.DocumentRepository;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class DocumentServiceImpl extends AbstractCrudService<Document, Integer> implements DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository repository) {
        super(repository);
        this.documentRepository = repository;
    }

    @Override
    public Page<Document> search(String keyword, Pageable pageable) {
        return documentRepository.findByTitleContaining(keyword, pageable);
    }


}

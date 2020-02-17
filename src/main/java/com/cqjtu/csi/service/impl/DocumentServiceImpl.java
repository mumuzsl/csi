package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.dto.DocumentDTO;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.repository.DocumentRepository;
import com.cqjtu.csi.repository.UserRepository;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class DocumentServiceImpl extends AbstractCrudService<Document, Integer> implements DocumentService {
    private final DocumentRepository documentRepository;
    private final UserService userService;

    public DocumentServiceImpl(DocumentRepository repository, UserService userService) {
        super(repository);
        this.documentRepository = repository;
        this.userService = userService;
    }

    @Override
    public Page search(String keyword, Pageable pageable) {
        return documentRepository
                .findByTitleContaining(keyword, pageable)
                .map(this::convert);
    }

    @Override
    public Page pageBy(Pageable pageable) {
        return documentRepository
                .findAll(PageUtils.of(pageable))
                .map(this::convert);
    }

    @Override
    public DocumentDTO convertById(Integer id) {
        return convert(getOne(id));
    }

    @Override
    public DocumentDTO convert(Document document) {
        DocumentDTO documentDTO = new DocumentDTO().convertFrom(document);
        Optional.ofNullable(document.getUserId())
                .map(userService::getOne)
                .map(User::getUsername)
                .ifPresent(documentDTO::setUsername);
        return documentDTO;
    }
}

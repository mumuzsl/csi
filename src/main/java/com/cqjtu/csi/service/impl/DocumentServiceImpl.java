package com.cqjtu.csi.service.impl;

import cn.hutool.core.io.FileUtil;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.DataException;
import com.cqjtu.csi.model.dto.DocumentDTO;
import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.repository.DocumentRepository;
import com.cqjtu.csi.repository.UserRepository;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.FileUtils;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Arrays;
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
        Assert.notNull(document, "document not be null");

        DocumentDTO documentDTO = new DocumentDTO().convertFrom(document);
        Optional.ofNullable(document.getUserId())
                .map(userService::addUsername)
                .ifPresent(documentDTO::setUsername);

        return documentDTO;
    }


    @Override
    public Document update(Document document) {
        Assert.notNull(document, "document not be null");

        Integer id = document.getId();
        delDocumentById(id);

        return super.updateById(id, document);
    }

    @Async
    public void delDocumentById(Integer id) {
        Optional.ofNullable(id)
                .map(this::getOne)
                .map(Document::getFilename)
                .map(CsiConst::toDocumentPath)
                .map(FileUtil::del);
    }
}

package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.repository.NoticeRepository;
import com.cqjtu.csi.service.NoticeService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class NoticeServiceImpl extends AbstractCrudService<Notice, Integer> implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository repository) {
        super(repository);
        this.noticeRepository = repository;
    }

    @Override
    public Page<Notice> search(String keyword, String content, Pageable pageable) {
        return noticeRepository.findByTitleContainingAndContentContaining(keyword, content, pageable);
    }
}

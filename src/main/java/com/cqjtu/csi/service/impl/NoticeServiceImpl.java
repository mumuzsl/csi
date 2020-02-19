package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.model.dto.NoticeDTO;
import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.NoticeParam;
import com.cqjtu.csi.repository.NoticeRepository;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.service.NoticeService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.service.base.ConvertDTO;
import com.cqjtu.csi.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Service
public class NoticeServiceImpl extends AbstractCrudService<Notice, Integer> implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserService userService;

    public NoticeServiceImpl(NoticeRepository repository, UserService userService) {
        super(repository);
        this.noticeRepository = repository;
        this.userService = userService;
    }

    @Override
    public Page search(Notice key, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", contains)
                .withMatcher("content", contains);

        Page<Notice> page = noticeRepository.findAll(Example.of(key, matcher), pageable);

        return page.map(this::convert);
    }


    @Override
    public NoticeDTO convert(Notice notice) {
        Assert.notNull(notice, "notice not be null");

        NoticeDTO noticeDTO = new NoticeDTO().convertFrom(notice);
        Optional.ofNullable(notice.getUserId())
                .map(userService::addUsername)
                .ifPresent(noticeDTO::setUsername);

        return noticeDTO;
    }

    @Override
    public NoticeDTO convertById(Integer id) {
        return convert(getOne(id));
    }

    @Override
    public Page pageBy(Pageable pageable) {
        return noticeRepository.fill(pageable);
    }
}

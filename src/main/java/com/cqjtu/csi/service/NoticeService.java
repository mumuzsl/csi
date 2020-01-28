package com.cqjtu.csi.service;

import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author mumu
 * @date 2020/1/20
 * @see com.cqjtu.csi.service.impl.NoticeServiceImpl
 */
public interface NoticeService extends CrudService<Notice, Integer> {

    /**
     * @param keyword
     * @param content
     * @param pageable
     * @return
     */
    Page<Notice> search(String keyword, String content, Pageable pageable);

}

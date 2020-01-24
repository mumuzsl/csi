package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.service.NoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mumu
 * @date 2020/1/20
 */
@RestController
@RequestMapping(value = "/notice")
public class NoticeController {

    private NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
}

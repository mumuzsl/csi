package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.NoticeService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Api("公告管理接口")
@RestController
@RequestMapping(value = "/api/notice")
public class NoticeController {

    private NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "status", required = false, defaultValue = "") String content, @PageableDefault Pageable pageable) {
        return null == keyword ? noticeService.pageBy(pageable) : noticeService.search(keyword, content, pageable);
    }

    /**
     * 添加（插入）
     *
     * @param  notice 添加的数据
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")  @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody Notice notice) {
        noticeService.insert(notice);
        return BaseUtils.insertSucceed();
    }

    /**
     * 删除
     *
     * @param ids 需要删除的id集
     * @return 删除成功BaseResponse
     */
    @PostMapping("a/delete")
    public BaseResponse delete(@RequestBody List<Integer> ids) {
        noticeService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param notice 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return BaseUtils.updateSucceed();
    }
}

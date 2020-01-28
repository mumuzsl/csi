package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.NoticeService;
import com.cqjtu.csi.utils.BaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "status", required = false, defaultValue = "") String content, @PageableDefault Pageable pageable) {
        return null == keyword ? noticeService.pageBy(pageable) : noticeService.search(keyword, content, pageable);
    }

    /**
     * 添加（插入）
     *
     * @param json 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    public BaseResponse insert(@RequestBody String json) {
        noticeService.insert(JSON.parseObject(json, Notice.class));
        return BaseUtils.insertSucceed();
    }

    /**
     * 删除
     *
     * @param ids 需要删除的id集
     * @return 删除成功BaseResponse
     */
    @PostMapping("a/delete")
    public BaseResponse delete(@RequestBody String ids) {
        Collection<Integer> collection = JSON.parseObject(ids, new TypeReference<Collection<Integer>>() {});
        noticeService.removeInBetch(collection);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param id   需要更新的数据id
     * @param json 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update/{id:\\d+}")
    public BaseResponse update(@PathVariable("id") Integer id, @RequestBody String json) {
        noticeService.update(JSON.parseObject(json, Notice.class));
        return BaseUtils.updateSucceed();
    }
}

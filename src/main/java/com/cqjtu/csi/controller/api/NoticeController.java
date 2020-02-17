package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.dto.DocumentDTO;
import com.cqjtu.csi.model.dto.NoticeDTO;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.NoticeParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.NoticeService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Api("公告管理接口")
@RestController
@RequestMapping(value = "/api/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    public NoticeController(NoticeService noticeService, UserService userService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }

    @GetMapping
    public Page search(@RequestParam(value = "keyword", required = false) String keyword, NoticeParam noticeParam, @PageableDefault Pageable pageable) {
        return null == keyword ? noticeService.pageBy(PageUtils.of(pageable)) : noticeService.search(noticeParam.convertTo(), PageUtils.of(pageable));
    }

    @GetMapping(value = "{id:\\d+}")
    public NoticeDTO one(@PathVariable("id") Integer id) {
        return noticeService.convertById(id);
    }

    /**
     * 添加（插入）
     *
     * @param notice 添加的数据
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody NoticeParam notice) {
        noticeService.insert(notice.convertTo());
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

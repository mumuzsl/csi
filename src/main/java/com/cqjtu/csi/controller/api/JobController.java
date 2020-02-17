package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.entity.Job;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.JobService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.PageUtils;
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
@Api("职位管理接口")
@RestController
@RequestMapping(value = "/api/job")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? jobService.pageBy(pageable) : jobService.search(keyword, PageUtils.of(pageable));
    }

    /**
     * 添加（插入）
     *
     * @param job 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody Job job) {
        jobService.insert(job);
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
        jobService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param job 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody Job job) {
        jobService.update(job);
        return BaseUtils.updateSucceed();
    }

}

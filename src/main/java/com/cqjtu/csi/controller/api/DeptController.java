package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.DeptService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mumu
 * @date 2020/1/20
 */
@Api(tags = {"部门管理接口"})
@RestController
@RequestMapping(value = "/api/dept")
public class DeptController {

    private DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("{deptId:\\d+}")
    public Dept getBy(@PathVariable(name = "deptId") Integer deptId) {
        return deptService.getById(deptId);
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? deptService.pageBy(pageable) : deptService.search(keyword, pageable);
    }

    /**
     * 添加（插入）
     *
     * @param dept 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody Dept dept) {
//        deptService.insert(JSON.parseObject(json, Dept.class));
        deptService.insert(dept);
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
//        Collection<Integer> collection = JSON.parseObject(ids, new TypeReference<Collection<Integer>>() {});
        deptService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param dept 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody Dept dept) {
        deptService.update(dept);
        return BaseUtils.updateSucceed();
    }
}

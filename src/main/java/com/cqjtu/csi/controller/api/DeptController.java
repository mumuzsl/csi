package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.DeptService;
import com.cqjtu.csi.utils.BaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author mumu
 * @date 2020/1/20
 */
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

    private DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("{deptId:\\d+}")
    public Dept getBy(@PathVariable(name = "deptId") Integer deptId) {
        return deptService.getById(deptId);
    }

//    @GetMapping
//    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
//        System.out.println(keyword);
//        return null == keyword ? deptService.pageBy(keyword) : deptService.search(keyword, pageable);
//    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault Pageable pageable) {
        return null == keyword ? deptService.pageBy(pageable) : deptService.search(keyword, pageable);
    }

    @PostMapping("a/insert")
    public BaseResponse insert() {
        return BaseUtils.insertSucceed();
    }
}

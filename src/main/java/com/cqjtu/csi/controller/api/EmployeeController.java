package com.cqjtu.csi.controller.api;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.dto.DocumentDTO;
import com.cqjtu.csi.model.dto.EmployeeDTO;
import com.cqjtu.csi.model.entity.Employee;
import com.cqjtu.csi.model.param.EmployeeParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.EmployeeService;
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
@Api("员工管理接口")
@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("list")
    public List list() {
        return employeeService.listAll();
    }

    @GetMapping
    public Page search(@RequestParam(value = "keyword", required = false) String keyword, EmployeeParam param, @PageableDefault Pageable pageable) {
        return null == keyword ? employeeService.pageBy(PageUtils.of(pageable)) : employeeService.searchToDto(param.convertTo(), PageUtils.of(pageable));
    }

    @GetMapping(value = "{id:\\d+}")
    public EmployeeDTO one(@PathVariable("id") Integer id) {
        return employeeService.convertById(id);
    }

    /**
     * 添加（插入）
     *
     * @param employee 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody Employee employee) {
        employeeService.insert(employee);
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
        employeeService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param employee 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return BaseUtils.updateSucceed();
    }
}

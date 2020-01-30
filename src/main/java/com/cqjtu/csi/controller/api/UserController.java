package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @author mumu
 * @date 2020/1/11
 */
@Api("用户管理接口")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "status", required = false, defaultValue = "0") String status, @PageableDefault Pageable pageable) {
        return null == keyword ? userService.pageBy(pageable) : userService.search(keyword, status, pageable);
    }

    /**
     * 添加（插入）
     *
     * @param user 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody User user) {
        userService.insert(user);
        return BaseUtils.insertSucceed();
    }

    /**
     * 删除
     *
     * @param ids 需要删除的id集
     * @return 删除成功BaseResponse
     */
    @PostMapping("a/delete")
    @ApiOperation("删除数据接口")
    public BaseResponse delete(@RequestBody List<Integer> ids) {
        userService.removeInBetch(ids);
        return BaseUtils.deleteSucceed();
    }

    /**
     * 更新
     *
     * @param user 更新的数据
     * @return 更新成功BaseResponse
     */
    @PostMapping("a/update}")
    public BaseResponse update(@RequestBody User user) {
        userService.update(user);
        return BaseUtils.updateSucceed();
    }
}
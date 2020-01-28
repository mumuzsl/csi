package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.csi.model.entity.Dept;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author mumu
 * @date 2020/1/11
 */
@RestController
@RequestMapping(value = "/user")
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
     * @param json 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    public BaseResponse insert(@RequestBody String json) {
        userService.insert(JSON.parseObject(json, User.class));
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
        userService.removeInBetch(collection);
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
        userService.update(JSON.parseObject(json, User.class));
        return BaseUtils.updateSucceed();
    }
}
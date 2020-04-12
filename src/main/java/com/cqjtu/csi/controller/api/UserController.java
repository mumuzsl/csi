package com.cqjtu.csi.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "status", required = false, defaultValue = "") String status, @PageableDefault Pageable pageable) {
        return null == keyword ? userService.pageBy(pageable) : userService.search(keyword, status, PageUtils.of(pageable));
    }

    @GetMapping(value = "detail")
    public User detail(@RequestHeader("token") String token) {
        return userService
                .getByToken(token)
                .orElse(null);
    }

    //    @GetMapping
    @Deprecated
    public String search2(String keyword, String status, Pageable pageable, HttpServletResponse response) {
        response.setContentType("text/plain");
        Page search = search(keyword, status, null);
        Map<String, Object> map = new HashMap<String, Object>(4);

        map.put("code", 0);
        map.put("msg", "");
        map.put("count", search.getTotalElements());
        map.put("data", JSONObject.toJSON(search.getContent()));

        return JSONObject.toJSONString(map);
    }

    /**
     * 添加（插入）
     *
     * @param user 数据json
     * @return 添加成功BaseResponse
     */
    @PostMapping("a/insert")
    @ApiOperation("添加数据接口")
    public BaseResponse insert(@RequestBody UserParam user) {
        userService.insert(user.convertTo());
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
    @PostMapping("a/update")
    public BaseResponse update(@RequestBody User user) {
        userService.update(user);
        return BaseUtils.updateSucceed();
    }
}
package com.cqjtu.csi.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.cqjtu.csi.core.ControllerExceptionHandler;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/1/21
 */
@Api("全局接口")
@Controller
@RequestMapping
@CrossOrigin
public class MainController extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";
    private static final String ERROR_TEMPLATE = "error/error";
    private static final String NOT_FOUND_TEMPLATE = "error/404";
    private static final String INTERNAL_SERVER_ERROR_TEMPLATE = "error/500";

    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    private UserService userService;

    public MainController(ErrorAttributes errorAttributes, UserService userService) {
        super(errorAttributes);
        this.userService = userService;
    }

    @GetMapping(value = "/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.error("Request URL: [{}], URI: [{}], Request Method: [{}], IP: [{}]",
                request.getRequestURL(),
                request.getRequestURI(),
                request.getMethod(),
                ServletUtil.getClientIP(request));

        Map<String, Object> errorDetail = getErrorAttributes(request, false);
        model.addAttribute("error", errorDetail);

        log.debug("Error detail: [{}]", errorDetail);

        return ERROR_TEMPLATE;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @PostMapping
    public void proxy(@RequestBody String body) {
        System.out.println(body);
    }

    @PostMapping(value = "register")
    @ApiOperation("注册")
    @ResponseBody
    public BaseResponse register(@RequestBody @Valid UserParam userParam) {
        userService.register(userParam);
        return BaseResponse.ok(CsiConst.REGISTER_SUCCESS);
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @PostMapping(value = "logout")
    @ResponseBody
    public BaseResponse logout(@RequestBody String json) {
        AuthToken authToken = JSON.parseObject(json, AuthToken.class);
        userService.logout(authToken);
        return BaseResponse.ok("已退出");
    }
}

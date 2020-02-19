package com.cqjtu.csi.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.cqjtu.csi.core.ControllerExceptionHandler;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.handle.FailureHandler;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/1/21
 */
@Controller
@RequestMapping
@CrossOrigin
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final UserService userService;
    private final DocumentService documentService;

    public MainController(UserService userService,
                          DocumentService documentService) {
        this.userService = userService;
        this.documentService = documentService;
    }

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("index.html");
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

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return userService.login(loginParam);
    }

//    @PostMapping(value = "logout")
//    @ResponseBody
//    public BaseResponse logout(@RequestBody String json) {
//        AuthToken authToken = JSON.parseObject(json, AuthToken.class);
//        userService.logout(authToken);
//        return BaseResponse.ok("已退出");
//    }

    @GetMapping(value = "logout")
    public String logout(@RequestParam("token") String token) {
//        userService.logout(token);
        return "logout";
    }

    @GetMapping(value = "download/{id:\\d+}")
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        try {
            Document one = documentService.getOne(id);
            File file = new File(CsiConst.DOCUMENT_DIR + one.getFilename());

            response.setContentType(FileTypeUtil.getType(file));
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=" +
                            BaseUtils.webStr(one.getTitle()) + "." +
                            FileTypeUtil.getType(file));
            response.setContentLengthLong(file.length());

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[0x1000];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

        } catch (NotFoundException | IORuntimeException e) {
            throw new BadRequestException("文件不存在");
        }
    }
}

package com.cqjtu.csi.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IORuntimeException;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

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

    @GetMapping(value = "face/register")
    public void faceRegister() {

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

    @GetMapping(value = "logout")
    public String logout(@RequestHeader("token") String token) {
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

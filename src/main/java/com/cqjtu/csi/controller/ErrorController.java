package com.cqjtu.csi.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.cqjtu.csi.core.ControllerExceptionHandler;
import com.cqjtu.csi.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/2/8
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";
    private static final String ERROR_TEMPLATE = "error/error";
    private static final String NOT_FOUND_TEMPLATE = "error/404";
    private static final String INTERNAL_SERVER_ERROR_TEMPLATE = "error/500";


    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final ErrorProperties errorProperties;

    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes);
        this.errorProperties = serverProperties.getError();
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @GetMapping
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

    @GetMapping("{code:\\d+}")
    public String handleError(@PathVariable("code") Integer code, Model model) {

        Map<String, Object> errorDetail = new HashMap<>(3);
        errorDetail.put("status", code);
        errorDetail.put("error", HttpStatus.valueOf(code).getReasonPhrase());
        errorDetail.put("message", HttpUtils.getHttpStatusMsg(code));
        model.addAttribute("error", errorDetail);

        return ERROR_TEMPLATE;
    }


}

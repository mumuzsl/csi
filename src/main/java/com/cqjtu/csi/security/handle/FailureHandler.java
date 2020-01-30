package com.cqjtu.csi.security.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.exception.BaseException;
import com.cqjtu.csi.model.support.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mumu
 * @date 2020/1/29
 */
public class FailureHandler {

    public void doHandle(HttpServletRequest request, HttpServletResponse response, BaseException exception) throws ServletException, IOException {
        BaseResponse<Object> errorDetail = new BaseResponse<>();

        errorDetail.setStatus(exception.getStatus().value());
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setData(exception.getErrorData());

//        if (!productionEnv) {
//            errorDetail.setDevMessage(ExceptionUtils.getStackTrace(exception));
//        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(exception.getStatus().value());
        response.getWriter().write(JSONObject.toJSONString(errorDetail));

    }
}

package com.cqjtu.csi.core;

import com.alibaba.fastjson.JSON;
import com.cqjtu.csi.exception.BaseException;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.filter.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

/**
 * @author mumu
 * @date 2020/1/21
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public BaseResponse handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        return baseResponse;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return baseResponse;
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse handleHaloException(BaseException e) {
        BaseResponse<Object> baseResponse = handleBaseException(e);
        baseResponse.setStatus(e.getStatus().value());
//        baseResponse.setDataToJSON(e.getErrorData());
        return baseResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleGlobalException(Exception e) {
        BaseResponse baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        log.error("Captured an exception", t);

        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(t.getMessage());

        return baseResponse;
    }
}

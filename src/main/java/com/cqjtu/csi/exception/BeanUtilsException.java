package com.cqjtu.csi.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 * @author mumu
 * @date 2020/1/12
 */
public class BeanUtilsException extends BaseException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

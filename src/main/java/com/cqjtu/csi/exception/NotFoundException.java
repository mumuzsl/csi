package com.cqjtu.csi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

}

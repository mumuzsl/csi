package com.cqjtu.csi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class AuthenticationException extends BaseException {


    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}

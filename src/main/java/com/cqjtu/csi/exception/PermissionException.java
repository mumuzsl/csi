package com.cqjtu.csi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author mumu
 * @date 2020/4/18
 */
public class PermissionException extends BaseException {

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}

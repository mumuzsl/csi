package com.cqjtu.csi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author mumu
 * @date 2020/1/24
 */
public class FaceClientException extends BaseException {

    public FaceClientException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

package com.cqjtu.csi.model.support;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class BaseResponse<T> implements Serializable {

    /**
     * Response status.
     */
    private Integer status;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response development message
     */
    private String devMessage;

    /**
     * Response data
     */
    private Object data;

    public BaseResponse() {}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * JSON化data，使该类能够携带更多类型的数据，也避免了可能出现的序列化异常。
     *
     * @param data
     */
    public void setDataToJSON(Object data) {
        setData(JSON.toJSON(data));
    }

    public BaseResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Creates an ok result with message and data. (Default status is 200)
     *
     * @param data    result data
     * @param message result message
     * @return ok result with message and data
     */
    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message, @Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * Creates an ok result with message only. (Default status is 200)
     *
     * @param message result message
     * @return ok result with message only
     */
    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message) {
        return ok(message, null);
    }

    public static <T> BaseResponse<T> to400(@Nullable String message) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    /**
     * Creates an ok result with data only. (Default message is OK, status is 200)
     *
     * @param data data to response
     * @param <T>  data type
     * @return base response with data
     */
    public static <T> BaseResponse<T> ok(@NonNull T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }
}

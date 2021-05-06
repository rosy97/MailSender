package com.pingan.mail.dto;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    final static int OK = 0; // 成功执行的状态码

    private int code = OK;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.pingan.mail.exception;

public class BaseException extends RuntimeException {

    static final long serialVersionUID = 531289719074536997L;

    public int code = -1;
    public String msg = null;

    public BaseException(int statusCode,String msg){
        super(msg);
        this.code = statusCode;
        this.msg = msg;
    }

    public BaseException(int statusCode,String msg,Throwable cause){
        super(msg, cause);
        this.code = statusCode;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.pingan.mail.exception;

public class MailServiceException extends BaseException {

    static final long serialVersionUID = 431219978074532037L;

    public static final int CODE = 111;
    public static final String MESSAGE = "邮箱输入错误";

    public MailServiceException(){
        super(CODE,MESSAGE);
    }

    public MailServiceException(Throwable cause){
        super(CODE,MESSAGE,cause);
    }
}

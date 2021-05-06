package com.pingan.mail.exception;

import com.pingan.mail.dto.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Response handlerMailServiceException(BaseException e){
        return Response.fail(e.getCode(),e.getMsg());
    }
}

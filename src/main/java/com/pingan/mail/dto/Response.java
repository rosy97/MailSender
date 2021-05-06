package com.pingan.mail.dto;

public class Response extends BaseResponse<Object> {

    public static Response ok(Object data){
        Response response = new Response();
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static Response ok(){
        Response response = ok(null);
        return response;
    }

    public static Response fail(int code, String msg){
        Response response = new Response();
        response.setCode(code);
        response.setMessage(msg);

        return response;
    }


}

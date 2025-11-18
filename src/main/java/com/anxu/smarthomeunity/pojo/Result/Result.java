package com.anxu.smarthomeunity.pojo.Result;

import lombok.Data;

import java.util.Map;

@Data
public class Result {
    private Integer code;//业务状态码，1 表示成功，0 表示失败
    private String msg;//提示信息，描述字符串
    private Object data;//返回的数据

    public static Result success(){
        Result r = new Result();
        r.code = 1;
        r.msg = "success";
        return r;
    }
    public static Result success(String msg){
        Result r = new Result();
        r.code = 1;
        r.msg = msg;
        return r;
    }
    public static Result success(Object data){
        Result r = success();
        r.data = data;
        return r;
    }
    public static Result error(String msg){
        Result r = new Result();
        r.code = 0;
        r.msg = msg;
        return r;
    }
}

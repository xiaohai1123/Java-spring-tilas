package com.xiaohai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;   //响应码,1:成功;2:失败
    private String msg;     //响应信息，描述字符串
    private Object data;    //返回的数据

    public static Result success() {        //增删改 成功响应
        return new Result(1,"success",null);
    }

    public static Result success(Object data) {     //查询 成功响应
        return new Result(1,"success",data);
    }

    public static Result success(String msg) {        //增删改 成功响应
        return new Result(0,msg,null);
    }
}

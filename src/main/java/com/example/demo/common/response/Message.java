package com.example.demo.common.response;

/**
 * @author MAIBENBEN
 */
public interface Message {

    String SUCCESS = "成功";
    String UNAUTHORIZED = "登录已失效，请重新登录";
    String FORBIDDEN = "无权限访问";
    String NOTFOUND = "资源不存在";
    String FAILURE = "系统错误，请联系管理员";
    String VALID_ERROR= "参数验证失败";

}

package com.example.demo.common.response;

public interface Code {

    int SUCCESS = 200;       //接口请求成功
    int UNAUTHORIZED = 401; //无效凭证
    int FORBIDDEN = 403;    //拒绝访问
    int NOTFOUND = 404;    //资源未找到
    int FAILURE = 500;      //接口请求失败


}

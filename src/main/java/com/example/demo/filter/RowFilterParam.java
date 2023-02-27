package com.example.demo.filter;

import lombok.Data;

/**
 * 行级别数据权限，请求参数需要继承该类
 */
@Data
public abstract class RowFilterParam {

    /**
     * 拦截处理后传递到方法里的数据
     */
    private String data;

}

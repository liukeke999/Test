package com.example.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页对象
 */
@Data
@AllArgsConstructor
public class ResultPage<T> {

    private int pageNum;
    private int pageSize;
    private long total;
    private List<T> list;

}

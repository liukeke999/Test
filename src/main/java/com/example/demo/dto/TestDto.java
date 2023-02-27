package com.example.demo.dto;


import com.example.demo.filter.RowFilterParam;
import lombok.Data;

/**
 * 测试传参
 */
@Data
public class TestDto extends RowFilterParam {

    private String name;

    private Integer age;
}

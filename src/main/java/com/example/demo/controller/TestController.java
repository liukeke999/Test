package com.example.demo.controller;


import com.example.demo.filter.ParamFilter;
import com.example.demo.dto.TestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @ParamFilter
    @PostMapping("/filter")
    public void test01(@RequestBody TestDto dto){
        //方法上面加入自定义的注解，自然就可以获取到处理后的值
        System.out.println(dto.getData());

    }
}

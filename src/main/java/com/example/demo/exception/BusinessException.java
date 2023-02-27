package com.example.demo.exception;

import com.example.demo.common.enums.ExceptionEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    /*
     * 模版异常
     */
    private ExceptionEnums exceptionEnums;

    /*
     * 自定义异常信息
     */
    private String errorDetail;

    public BusinessException(){}

    public BusinessException(String data) {
        super(data);
    }
    /**
     * 带自定义异常信息的构造方法
     * @param exceptionEnums
     * @param errorDetail
     */
    public BusinessException(ExceptionEnums exceptionEnums, String errorDetail){
        this.exceptionEnums = exceptionEnums;
        this.errorDetail = errorDetail;
    }

    /**
     * 模版异常的构造方法
     * @param exceptionEnums
     */
    public BusinessException(ExceptionEnums exceptionEnums){
        this.exceptionEnums = exceptionEnums;
    }


}

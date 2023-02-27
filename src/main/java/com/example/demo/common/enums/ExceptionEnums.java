package com.example.demo.common.enums;

public enum ExceptionEnums {

    SERVER_ALIYUN_UPLOAD_ERROR("1000","上传阿里云失败"),
    SERVER_OTHER_ERROR("1099","其他异常");//枚举类如果写方法的话，此处需要写分号

    private String code;

    private String msg;

    ExceptionEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ExceptionEnums statOf(String code) {
        for (ExceptionEnums state : values())
            if (state.getCode().equals(code))
                return state;
        return null;
    }
}

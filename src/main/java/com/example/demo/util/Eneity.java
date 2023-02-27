package com.example.demo.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "baidu")
@Data
public  class Eneity {

    private  String apiKey;
    private  String secretKey;
}

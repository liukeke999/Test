package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    @Qualifier("redisTemplateConfig")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws JsonProcessingException {
        User user = new User("liuke", 2);
        String object = new ObjectMapper().writeValueAsString(user);


        redisTemplate.opsForValue().set("user",object);
        redisTemplate.opsForValue().set("user2",user);

        System.out.println("正常输出"+redisTemplate.opsForValue().get("user"));
        System.out.println("非正常输出"+redisTemplate.opsForValue().get("user2"));
        JSONObject user1 = JSONObject.parseObject(redisTemplate.opsForValue().get("user").toString());
        System.out.println("1"+user1.get("name"));
        Object user21 = redisTemplate.opsForValue().get("user2");
        JSONObject json=(JSONObject) JSONObject.toJSON(user21);
        System.out.println("2"+json.get("name"));

    }

}

package com.example.demoredis.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YmlUtilTest {

    @Autowired
    YmlUtil ymlUtil;

    @Test
    void getProperty() {
        String property = ymlUtil.getProperty("spring.redis.host");
        System.out.println("property = " + property);
    }
}
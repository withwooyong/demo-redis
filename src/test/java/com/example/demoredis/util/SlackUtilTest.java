package com.example.demoredis.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackUtilTest {

    @Autowired
    SlackUtil slackUtil;

    @Test
    void send() {
        slackUtil.send("dev-git test message");
    }
}
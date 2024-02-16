package com.example.demoredis.util;

import com.example.demoredis.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
//@ActiveProfiles("live")
class RedisUtilTest {

    @Autowired
    RedisUtil redisUtil;

//    @Autowired
//    RedisTemplate<String, List<Message>> redisTemplate;

    @Test
    void redisTest() {
        String KEY_FORMAT = "ai::%s::%s";
        String user = "anonymous"; // userSeq (로그인시 userSeq)
        String key = String.format(KEY_FORMAT, user, "2ceee3b2-de24-43b5-819c-110ff5722b95");
        Object value = redisUtil.getValue(key);
        System.out.println("value = " + value);
//        System.out.println("value = " + CommonUtil.prettyPrintJson(value.toString()));
    }

    @Test
    void messageTest() {
        List<Message> messages = List.of(
                Message.builder().role("system").content("system message").build(),
                Message.builder().role("user").content("user message").build(),
                Message.builder().role("assistant").content("assistant message").build()
        );

        String jsonString = CommonUtil.toJson(messages);
        System.out.println("jsonString = " + jsonString);

        List<Message> object = CommonUtil.toList(jsonString, Message.class);
        for (Message message : object) {
            System.out.println("message = " + message);
        }

    }

    @Test
    void opsForValueTest() {
        List<Message> messages = List.of(
                Message.builder().role("system").content("system message").build(),
                Message.builder().role("user").content("user message").build(),
                Message.builder().role("assistant").content("assistant message").build()
        );
//        redisTemplate.opsForValue().set("opsForValue", messages, Duration.ofMinutes(1));
//        List<Message> value = redisTemplate.opsForValue().get("opsForValue");
        // 317 bytes
//        redisClient.setValue("opsForValue", messages, Duration.ofMinutes(1));
        // 221 bytes
        redisUtil.setValue("opsForValue", CommonUtil.toJson(messages), Duration.ofMinutes(1));
        Object value = redisUtil.getValue("opsForValue");
        System.out.println("value = " + value);
        List<Message> list = CommonUtil.toList((String) value, Message.class);
        for (Message message : list) {
            System.out.println("message = " + message);
        }


//        redisClient.delete("opsForValue");
//        value = redisClient.getValue("opsForValue");
//        System.out.println("value = " + value);

        // Object to List<Message>
//        List messageList = CommonUtil.toObject((String) value, List.class);
//        for (Object o : messageList) {
//            System.out.println("o = " + o);
//        }
    }

//    @Test
//    void hashOperationsTest() {
//        List<Message> messages = List.of(
//                Message.builder().role("system").content("system message").build(),
//                Message.builder().role("user").content("user message").build(),
//                Message.builder().role("assistant").content("assistant message").build()
//        );
//        redisClient.putHash("hashOperations", "hashKey", messages);
//        Object hash = redisClient.getHash("hashOperations", "hashKey");
//        System.out.println("hash = " + hash);
//
//        // Object to List<Message>
//        List<Message> messages1 = (List<Message>) hash;
//        for (Message message : messages1) {
//            System.out.println("message = " + message);
//        }
//    }

    @Test
    void leftPush() {
        redisUtil.leftPush("leftPush", "leftPushValue");
        for (int i = 0; i < 10; i++) {
            redisUtil.leftPush("leftPush", "leftPushValue" + i);
        }

        List<Object> leftPush = redisUtil.listRange("leftPush", 0, 9);
        for (Object o : leftPush) {
            System.out.println("o = " + o);
        }
    }

    @Test
    void leftPop() {
        Object leftPop = redisUtil.leftPop("leftPush");
        System.out.println("leftPop = " + leftPop);
    }

    @Test
    void rightPop() {
        Object rightPop = redisUtil.rightPop("leftPush");
        System.out.println("rightPop = " + rightPop);
    }

    @Test
    void rightPopAndLeftPush() {
        Object rightPopAndLeftPush = redisUtil.rightPopAndLeftPush("leftPush", "leftPush");
        System.out.println("rightPopAndLeftPush = " + rightPopAndLeftPush);
    }

    @Test
    void listIndex() {
        Object listIndex = redisUtil.listIndex("leftPush", 0);
        System.out.println("listIndex = " + listIndex);
        listIndex = redisUtil.listIndex("leftPush", 1);
        System.out.println("listIndex = " + listIndex);
    }

    @Test
    void listRange() {
        List<Object> listRange = redisUtil.listRange("leftPush", 0, 3);
        for (Object o : listRange) {
            System.out.println("o = " + o);
        }
    }

    @Test
    void listSet() { // index에 데이터를 넣거나 있으면 변경한다.
        redisUtil.listSet("leftPush", 1, "listSet10");
        Object listIndex = redisUtil.listIndex("leftPush", 1);
        System.out.println("listIndex = " + listIndex);
    }

    @Test
    void listSize() {
        Long listSize = redisUtil.listSize("leftPush");
        System.out.println("listSize = " + listSize);
    }

    @Test
    void listTrim() {
        redisUtil.listTrim("leftPush", 0, 3);
        List<Object> listRange = redisUtil.listRange("leftPush", 0, 3);
        for (Object o : listRange) {
            System.out.println("o = " + o);
        }
    }

    @Test
    void delete() {
        redisUtil.delete("leftPush");
        Long listSize = redisUtil.listSize("leftPush");
        System.out.println("listSize = " + listSize);
    }

    @Test
    void setValue() {
        redisUtil.setValue("setValue", "setValue", Duration.ofMinutes(1));
        Object value = redisUtil.getValue("setValue");
        System.out.println("value = " + value);
    }

    @Test
    void getValue() {
        Object value = redisUtil.getValue("setValue");
        System.out.println("value = " + value);
    }

    @Test
    void setValue2() {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        redisUtil.setValue("setValue2", "setValue2", 1, timeUnit);
        Object value = redisUtil.getValue("setValue2");
        System.out.println("value = " + value);
    }

    @Test
    void getValue2() {
        Object value = redisUtil.getValue("setValue2");
        System.out.println("value = " + value);
    }

    @Test
    void putHash() {
        redisUtil.putHash("putHash", "putHashKey1", "putHashValue1");
        Object hash = redisUtil.getHash("putHash", "putHashKey");
        System.out.println("hash = " + hash);
    }

    @Test
    void deleteHash() {
        redisUtil.deleteHash("putHash", List.of("putHashKey", "putHashKey1").toArray());
        Object hash = redisUtil.getHash("putHash", "putHashKey");
        System.out.println("hash = " + hash);
    }
}
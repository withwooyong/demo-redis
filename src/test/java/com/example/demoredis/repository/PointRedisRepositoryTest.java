package com.example.demoredis.repository;

import com.example.demoredis.util.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Random;
import java.util.Set;

@SpringBootTest
class PointRedisRepositoryTest {

    @Autowired
    PointRedisRepository pointRedisRepository;

    @Autowired
    RedisClient redisClient;

    @Test
    void ranking() {
//        for (int i = 0; i < 10; i++) {
//            Point point = Point.builder()
//                    .id("user" + i)
//                    .member(Member.builder().id("user" + i).name("user" + i).build())
//                    .content("content" + i)
//                    .score(i)
//                    .build();
//            pointRedisRepository.save("user" + i, i);
//        }

        ZSetOperations<Object, Object> zSetOperations = redisClient.getZSetOperations();
        for (int i = 0; i < 10; i++) {
            // make random int
            Random random = new Random();
            int score = random.nextInt(100);
            zSetOperations.add("ranking", "user" + i, score);
        }
    }

    @Test
    void reverseRangeWithScores() {
        ZSetOperations<Object, Object> zSetOperations = redisClient.getZSetOperations();
        Set<ZSetOperations.TypedTuple<Object>> ranking = zSetOperations.reverseRangeWithScores("ranking", 0, 9);
        assert ranking != null;
        for (ZSetOperations.TypedTuple<Object> tuple : ranking) {
            System.out.println("tuple = " + tuple.getValue() + " : " + tuple.getScore());
        }
    }

    @Test
    void range() {
        ZSetOperations<Object, Object> zSetOperations = redisClient.getZSetOperations();
        Set<Object> ranking = zSetOperations.range("ranking", 0, 2);
        assert ranking != null;
        for (Object user : ranking) {
            System.out.println("user = " + user);
        }
    }
}
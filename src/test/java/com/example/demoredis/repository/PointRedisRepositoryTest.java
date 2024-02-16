package com.example.demoredis.repository;

import com.example.demoredis.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

@SpringBootTest
class PointRedisRepositoryTest {

    @Autowired
    PointRedisRepository pointRedisRepository;

    @Autowired
    RedisUtil redisUtil;

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

        ZSetOperations<Object, Object> zSetOperations = redisUtil.getZSetOperations();
        for (int i = 0, j = 10; i < 10; i++, j--) {
            // make random int
            Random random = new Random();
            int score = random.nextInt(100);
            zSetOperations.add("ranking", "user1" + i, i);
            zSetOperations.add("ranking", "user2" + j, j);
//            zSetOperations.add("ranking", "user" + i, score);
        }
    }

    /**
     * 동점자가 여러명이면 자신의 랭킹을 제대로 확인할 수 없다.
     * 예를 들어 10점 5점 5점 5점의 score를 가진 4명이 존재한다면 랭킹확인시 5점 3명은 2등으로 표시되길 원하기 때문이다.
     * 이러한 문제를 해결하고자 reverseRangeByScore메서드를 사용
     */
    @Test
    void reverseRangeWithScores() {
        ZSetOperations<Object, Object> zSetOperations = redisUtil.getZSetOperations();
        Set<ZSetOperations.TypedTuple<Object>> ranking = zSetOperations.reverseRangeWithScores("ranking", 0, 9);
//        zSetOperations.reverseRangeByScore("ranking", 0, 9);
        assert ranking != null;
        for (ZSetOperations.TypedTuple<Object> tuple : ranking) {
            System.out.println("tuple = " + tuple.getValue() + " : " + tuple.getScore() + " : " + zSetOperations.reverseRank("ranking", Objects.requireNonNull(tuple.getValue())));
        }
        Double score29 = zSetOperations.score("ranking", "user29");
        Long rank29 = zSetOperations.rank("ranking", "user29");
        Long aLong29 = zSetOperations.reverseRank("ranking", "user29");
        System.out.println("score29 = " + score29 + " rank = " + rank29 + " aLong = " + aLong29);

        Double score19 = zSetOperations.score("ranking", "user19");
        Long rank19 = zSetOperations.rank("ranking", "user19");
        Long aLong19 = zSetOperations.reverseRank("ranking", "user19");
        System.out.println("score19 = " + score19 + " rank = " + rank19 + " aLong = " + aLong19);

        Set<Object> ranking1 = zSetOperations.reverseRange("ranking", 0, 9);
        assert ranking1 != null;
        for (Object user : ranking1) {
            System.out.println("user = " + user);
        }
        zSetOperations.rank("ranking", "user29");


        Set<Object> ranking2 = zSetOperations.reverseRangeByScore("ranking", 9, 9);
        assert ranking2 != null;
        for (Object user : ranking2) {
            System.out.println("user = " + zSetOperations.reverseRank("ranking", user) + " : " + user);
        }

    }

    @Test
    void range() {
        ZSetOperations<Object, Object> zSetOperations = redisUtil.getZSetOperations();
        Set<Object> ranking = zSetOperations.range("ranking", 0, 2);
        assert ranking != null;
        for (Object user : ranking) {
            System.out.println("user = " + user);
        }
    }
}
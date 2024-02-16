package com.example.demoredis.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LettuceSubscriber {

    private final RedisClient redisClient;

    public LettuceSubscriber(@Value("${spring.redis.host}") String host,
                             @Value("${spring.redis.port}") int port) {
        log.info("host: {}, port: {}", host, port);
        this.redisClient = RedisClient.create("redis://" + host + ":" + port);
    }

//    public LettuceSubscriber() {
//        log.info("host: {}, port: {}", host, port);
//        this.redisClient = RedisClient.create("redis://" + host + ":" + port);
//    }

    public void subscribeToChannel(String channel) {
        StatefulRedisPubSubConnection<String, String> pubSubConnection =
                redisClient.connectPubSub();
        pubSubConnection.addListener(new RedisPubSubListener<>() {
            @Override
            public void message(String channel, String message) {
                log.info("메시지 수신: " + message + " 채널: " + channel);
            }

            @Override
            public void message(String pattern, String channel, String message) {
                log.info("패턴 메시지 수신: " + message + " 패턴: " + pattern + " 채널: " + channel);
            }

            @Override
            public void subscribed(String channel, long count) {
                log.info("구독 시작: " + channel + " count: " + count);
            }

            @Override
            public void psubscribed(String pattern, long count) {
                log.info("패턴 구독 시작: " + pattern + " count: " + count);
            }

            @Override
            public void unsubscribed(String channel, long count) {
                log.info("구독 해지: " + channel + " count: " + count);
            }

            @Override
            public void punsubscribed(String pattern, long count) {
                log.info("패턴 구독 해지: " + pattern + " count: " + count);
            }
        });
        pubSubConnection.async().subscribe(channel);
        // 여기서는 예제를 간단히 하기 위해 연결을 닫지 않았습니다. 실제 사용시에는 적절한 시점에 연결을 닫아주어야 합니다.
    }

    public void shutdown() {
        if (redisClient != null) {
            log.info("shutdown");
            redisClient.shutdown();
        }
    }
}

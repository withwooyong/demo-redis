package com.example.demoredis.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LettucePublisher {

    private final RedisClient redisClient;

    public LettucePublisher(@Value("${spring.redis.host}") String host,
                            @Value("${spring.redis.port}") int port) {
        log.info("host: {}, port: {}", host, port);
        this.redisClient = RedisClient.create("redis://" + host + ":" + port);
    }

    public void publishMessage(String channel, String message) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> syncCommands = connection.sync();
            syncCommands.publish(channel, message);
        } // try-with-resources가 자동으로 연결을 닫습니다.
    }

    public void shutdown() {
        if (redisClient != null) {
            log.info("shutdown");
            redisClient.shutdown();
        }
    }
}

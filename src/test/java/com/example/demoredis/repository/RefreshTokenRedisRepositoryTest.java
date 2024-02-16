package com.example.demoredis.repository;

import com.example.demoredis.entity.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@SpringBootTest
class RefreshTokenRedisRepositoryTest {

    @Autowired
    RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Test
    void findByRefreshToken() {
    }

    @Test
    void save() throws UnknownHostException {
        for (int i = 0; i < 10; i++) {
            RefreshToken roleUser = RefreshToken.builder()
                    .id(String.valueOf(i))
                    .ip(InetAddress.getLocalHost().getHostAddress())
                    .authorities("ROLE_USER")
                    .refreshToken(UUID.randomUUID().toString())
                    .build();
            refreshTokenRedisRepository.save(roleUser);
        }
    }
}
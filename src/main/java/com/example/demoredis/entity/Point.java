package com.example.demoredis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "point", timeToLive = 3600) // seconds 단위이고, 만료되지 않게 하려면 -1L을 설정하면 된다.
public class Point {
    @Id
    private String id;
    private Member member;
    private String content;
    private int score;
}

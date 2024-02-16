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
@RedisHash(value = "member", timeToLive = 3600) // seconds 단위이고, 만료되지 않게 하려면 -1L을 설정하면 된다.
public class Member {

    @Id
    private String id;
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
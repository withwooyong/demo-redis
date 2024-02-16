package com.example.demoredis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "person2", timeToLive = 3600)
public class Person2 {

    @Id
    String id;
    /**
     * TODO Ted 사용하면 메모리 공간을 많이 차지해서 안좋은듯.
     * https://dkswnkk.tistory.com/709
     */
    @Indexed
    String firstname;
    String lastname;
    Address address;
}
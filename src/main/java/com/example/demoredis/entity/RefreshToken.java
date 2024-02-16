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
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "refresh", timeToLive = 3600)
public class RefreshToken {

    @Id
    private String id;
    private String ip;
    //    private Collection<? extends GrantedAuthority> authorities;
    private String authorities;
    @Indexed
    private String refreshToken;
}
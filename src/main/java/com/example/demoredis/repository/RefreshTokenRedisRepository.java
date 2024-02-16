package com.example.demoredis.repository;

import com.example.demoredis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    RefreshToken findByRefreshToken(String refreshToken);
}

package com.example.demoredis.repository;

import com.example.demoredis.entity.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}

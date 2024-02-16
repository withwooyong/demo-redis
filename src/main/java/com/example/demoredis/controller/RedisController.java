package com.example.demoredis.controller;

import com.example.demoredis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @RequestMapping("/pubSub")
    public ResponseEntity<String> pubSub(String message) {
        log.info("pubSub");
        redisService.pubSub(message);
        return ResponseEntity.ok("pubSub");
    }
}

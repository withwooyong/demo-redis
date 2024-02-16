package com.example.demoredis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class RedisService {

    private final LettucePublisher publisher;
    private final LettuceSubscriber subscriber;

    public void subscriberStart() {
//        new Thread(() -> subscriber.subscribeToChannel("testChannel")).start();
        boolean exists = checkIfThreadExists("subscriberThread");
        if (exists) {
            log.info("subscriberThread exists");
        } else {
            Thread subscriberThread = new Thread(() -> subscriber.subscribeToChannel("testChannel"));
            subscriberThread.setName("subscriberThread");
            subscriberThread.start();
            log.info("subscriberThread started");
        }
    }

    public boolean checkIfThreadExists(String threadName) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
//            log.info("threadName = {}", thread.getName());
            if (thread.getName().equals(threadName)) {
                return true; // 지정된 이름을 가진 스레드가 존재함
            }
        }
        return false; // 지정된 이름을 가진 스레드가 존재하지 않음
    }

    public void publishMessage() {
        publisher.publishMessage("testChannel", "Hello=" + new Random().nextInt(100));
    }

    public void pubSub(String message) {
        log.info("pubSub {}", message);
//        LettucePublisher publisher = new LettucePublisher();
//        LettuceSubscriber subscriber = new LettuceSubscriber();
        // 구독 시작
        subscriberStart();
        publisher.publishMessage("testChannel", "Hello=" + message);
        // 모든 작업이 완료된 후 리소스 정리
//        publisher.shutdown();
//        subscriber.shutdown();
    }
}

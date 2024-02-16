package com.example.demoredis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class RankingServiceTest {

    @Autowired
    RankingService rankingService;

    @Test
    void addScore() {
//        for (int i = 0; i < 10; i++) {
//            rankingService.addScore("user" + i, i);
//        }
        rankingService.addScore("user10", 1);
    }

    @Test
    void getRank() {
        Long rank1 = rankingService.getRank("user1");
        System.out.println("rank1 = " + rank1);
        Long rank10 = rankingService.getRank("user10");
        System.out.println("rank10 = " + rank10);
    }

    @Test
    @DisplayName("동일한 점수를 가진 사용자들에게 동일한 순위를 부여")
    void getUnifiedRank() {
        Long user1 = rankingService.getUnifiedRank("user1");
        System.out.println("user1 = " + user1);
        Long user10 = rankingService.getUnifiedRank("user10");
        System.out.println("user10 = " + user10);
    }

    @Test
    void getTopPlayers() {
        Set<Object> topPlayers = rankingService.getTopPlayers(1);
        for (Object topPlayer : topPlayers) {
            System.out.println("topPlayer = " + topPlayer);
        }
    }
}
package com.example.demoredis.service;

import com.example.demoredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class RankingService {

    private final RedisUtil redisUtil;
    private static final int TOPN = 10;
    private static final String PLAYER_SCORES = "playerScores";

    public void addScore(String player, double score) {
        redisUtil.getZSetOperations().add(PLAYER_SCORES, player, score);
    }

    /**
     * 동일 점수의 사용자들 중 먼저 삽입된 사용자가 더 낮은 순위를 가짐
     */
    public Long getRank(String player) {
        return redisUtil.getZSetOperations().reverseRank(PLAYER_SCORES, player);
    }

    /**
     * 동일한 점수를 가진 사용자들에게 동일한 순위를 부여
     */
    public Long getUnifiedRank(String player) {
        Double score = redisUtil.getZSetOperations().score(PLAYER_SCORES, player); // 사용자 점수 가져오기

        // 동일 점수를 가진 첫 번째 사용자 찾기
        Set<Object> playerScores = redisUtil.getZSetOperations().rangeByScore(RankingService.PLAYER_SCORES, score, score, 0, 1);

        for (Object playerScore : Objects.requireNonNull(playerScores)) {
            log.info("playerScore = " + playerScore);
        }
        return redisUtil.getZSetOperations().reverseRank(RankingService.PLAYER_SCORES, playerScores.iterator().next()); // 동일 점수를 가진 모든 사용자에게 이 순위 반환
    }

    public Set<Object> getTopPlayers(int topN) {
        // Get top N players. Note: Redis ranks are 0-based.
        return redisUtil.getZSetOperations().reverseRange(PLAYER_SCORES, 0, TOPN - 1);
    }


//    @Transactional
//    public void create(Member member, String content, int score) {
//        Point point = Point.createPoint(member, content, score);
//        member.setPoint(member.getPoint() + score);
//        redisClient.getZSetOperations().add("ranking", member.getName(), member.getPoint());
//        pointRedisRepository.save(point);
//    }
//
//    public List<Object> getRankingList() {
//        String key = "ranking";
//        ZSetOperations<Object, Object> zSetOperations = redisClient.getZSetOperations();
//        Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(key, 0, 10);
//        List<Object> collect = typedTuples.stream().map(ResponseRankingDto::convertToResponseRankingDto).collect(Collectors.toList());
//        return collect;
//    }
//
//    public Long getMyRank(String name) {
//        Long rank = 0L;
//        ZSetOperations<Object, Object> zSetOperations = redisClient.getZSetOperations();
//        Double score = zSetOperations.score("ranking", name);
//        Set<Object> ranking = zSetOperations.reverseRangeByScore("ranking", score, score, 0, 1);
//        for (String s : ranking) {
//            rank = zSetOperations.reverseRank("ranking", s);
//        }
//        return rank + 1; //index가 0부터 시작되어서 1 더해준다
//    }
}

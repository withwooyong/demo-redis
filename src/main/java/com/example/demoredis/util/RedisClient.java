package com.example.demoredis.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class RedisClient {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final ValueOperations<Object, Object> valueOperations;
    private final ListOperations<Object, Object> listOperations;
    private final HashOperations<Object, Object, Object> hashOperations;
    private final SetOperations<Object, Object> setOperations;
    private final ZSetOperations<Object, Object> zSetOperations;

    public RedisClient(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public SetOperations<Object, Object> getSetOperations() {
        return setOperations;
    }

    public ZSetOperations<Object, Object> getZSetOperations() {
        return zSetOperations;
    }

//    public void setOperationsTest() {
//        setOperations.add("test", "test1", "test2", "test3");
//        setOperations.pop("test");
//        setOperations.remove("test", "test1");
//    }
//
//    public void zSetOperationsTest() {
//        zSetOperations.add("test", "test1", 1);
//        zSetOperations.add("test", "test2", 2);
//        zSetOperations.add("test", "test3", 3);
//        zSetOperations.range("test", 0, 1);
//        zSetOperations.reverseRange("test", 0, 1);
//        zSetOperations.remove("test", "test1");
//        zSetOperations.score("test", "test2");
//    }

    // leftPush : 왼쪽에서부터 데이터를 넣는다.
    public void leftPush(String key, Object value) {
        listOperations.leftPush(key, value);
    }

    // rightPush : 오른쪽에서부터 데이터를 넣는다.
    public Object leftPop(String key) {
        return listOperations.leftPop(key);
    }

    // rightPop : 오른쪽에서부터 데이터를 꺼낸다.
    public Object rightPop(String key) {
        return listOperations.rightPop(key);
    }

    // rightPopAndLeftPush : 오른쪽에서부터 데이터를 꺼내서 왼쪽으로 넣는다.
    public Object rightPopAndLeftPush(String sourceKey, String destinationKey) {
        return listOperations.rightPopAndLeftPush(sourceKey, destinationKey);
    }

    // index : index에 해당하는 데이터를 가져온다.
    public Object listIndex(String key, long index) {
        return listOperations.index(key, index);
    }

    // range : start부터 end까지의 데이터를 가져온다.
    public List<Object> listRange(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    // set : index에 데이터를 넣거나 있으면 변경한다.
    public void listSet(String key, long index, Object value) {
        listOperations.set(key, index, value);
    }

    // size : 데이터의 크기를 가져온다.
    public Long listSize(String key) {
        return listOperations.size(key);
    }

    // trim : 데이터를 자른다. (지운다. 뒤에서 부터 삭제한다.)
    public void listTrim(String key, long start, long end) {
        listOperations.trim(key, start, end);
    }

    // 캐시 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // value 등록
    public void setValue(String key, Object value, Duration duration) {
        valueOperations.set(key, value, duration);
    }

    // value 등록
    public void setValue(String key, Object value, long timeout, TimeUnit timeUnit) {
        valueOperations.set(key, value, timeout, timeUnit);
    }

    // value 조회
    public Object getValue(String key) {
        return valueOperations.get(key);
    }

    // hash 등록 및 수정
    public void putHash(String key, Object hashKey, Object value) {
        hashOperations.put(key, hashKey, value);
    }

    // hash 조회
    public Object getHash(String key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

    // hash 삭제
    public void deleteHash(String key, Object... hashKeys) {
        hashOperations.delete(key, hashKeys);
    }
}

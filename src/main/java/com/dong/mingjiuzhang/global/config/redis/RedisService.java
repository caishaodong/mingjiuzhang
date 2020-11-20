package com.dong.mingjiuzhang.global.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author caishaodong
 * @Date 2020-10-10 11:17
 * @Description
 **/
@Component
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, String value, long second) {
        stringRedisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    public void getString(String key) {
        stringRedisTemplate.opsForValue().get(key);
    }

    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void getHash(String key, String hashKey) {
        redisTemplate.opsForHash().get(key, hashKey);
    }
}

package com.dong.mingjiuzhang.global.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author caishaodong
 * @Date 2020-10-10 11:17
 * @Description Redis工具类
 **/
@Component
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加String
     *
     * @param key
     * @param value
     * @param second
     */
    public void setString(String key, String value, long second) {
        stringRedisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 获取String
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除String
     *
     * @param key
     * @return
     */
    public Boolean deleteString(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 添加Long
     *
     * @param key
     * @param value
     * @param second
     */
    public void setLong(String key, Long value, long second) {
        redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 获取Long
     *
     * @param key
     * @return
     */
    public Long getLong(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return Objects.isNull(value) ? null : Long.valueOf(String.valueOf(value));
    }

    /**
     * 删除Long
     *
     * @param key
     * @return
     */
    public Boolean deleteLong(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 添加Hash
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取hash
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除hash
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Long deleteHash(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 删除Object
     *
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}

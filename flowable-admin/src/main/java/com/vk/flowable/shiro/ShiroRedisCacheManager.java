package com.vk.flowable.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class ShiroRedisCacheManager implements CacheManager {
    private RedisTemplate<String, Object> redisTemplate;
    
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<K, V>(name, redisTemplate);
    }
}
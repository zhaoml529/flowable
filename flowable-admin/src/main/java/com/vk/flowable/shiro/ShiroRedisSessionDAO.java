package com.vk.flowable.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class ShiroRedisSessionDAO extends CachingSessionDAO {
	private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDAO.class);

	//private final static String prefix = "oh-erp-shiro-session:";
	private final static String prefix = "vk:";

	private RedisTemplate<String, Object> redisTemplate;
	private int expireTime = 60000;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		logger.info("创建session:{}", session.getId());
		redisTemplate.opsForValue().set(prefix + sessionId.toString(), session, expireTime, TimeUnit.MILLISECONDS);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.info("获取session:{}", sessionId);
		return (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
	}

	@Override
	protected void doUpdate(Session session) {
		logger.info("更新session:{}", session.getId());
		session.setTimeout(expireTime);
		String key = prefix + session.getId().toString();
		if (!redisTemplate.hasKey(key)) {
			redisTemplate.opsForValue().set(key, session, expireTime, TimeUnit.MILLISECONDS);
		}
		redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void doDelete(Session session) {
		logger.info("删除session:{}", session.getId());
		redisTemplate.delete(prefix + session.getId().toString());
	}
}

package com.vk.flowable.shiro.context;

import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * 服务端会话管理
 */
public interface ShiroUserContextService {
	public Session getSession(String appKey, Serializable sessionId);
    public Serializable createSession(Session session);
    public void updateSession(String appKey, Session session);
    public void deleteSession(String appKey, Session session);
    public String[] getMappedValue(String appKey, String requestUri);
}

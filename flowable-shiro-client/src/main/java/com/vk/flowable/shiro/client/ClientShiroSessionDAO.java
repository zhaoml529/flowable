package com.vk.flowable.shiro.client;

import com.vk.flowable.shiro.context.ShiroUserContextService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

public class ClientShiroSessionDAO extends CachingSessionDAO {
    private ShiroUserContextService ctxService;
    private String appKey;

    public void setRemoteService(ShiroUserContextService ctxService) {
        this.ctxService = ctxService;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    protected void doDelete(Session session) {
    	ctxService.deleteSession(appKey, session);
    }

    @Override
    protected void doUpdate(Session session) {
    	ctxService.updateSession(appKey, session);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = ctxService.createSession(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session s =  ctxService.getSession(appKey, sessionId);
        return s;
    }
}

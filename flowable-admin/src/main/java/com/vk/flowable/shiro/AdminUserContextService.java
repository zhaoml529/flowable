package com.vk.flowable.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vk.flowable.domain.Permission;
import com.vk.flowable.enums.EnumPermissionType;
import com.vk.flowable.service.PermissionService;
import com.vk.flowable.shiro.context.ShiroUserContextService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Component
public class AdminUserContextService implements ShiroUserContextService {
	PatternMatcher pathMatcher = new AntPathMatcher();

	@Resource
	private SessionDAO sessionDAO;

	@Resource
	private PermissionService permissionService;
	
	@Override
	public Session getSession(String appKey, Serializable sessionId) {
		return sessionDAO.readSession(sessionId);
	}

	@Override
	public Serializable createSession(Session session) {
		return sessionDAO.create(session);
	}

	@Override
	public void updateSession(String appKey, Session session) {
		sessionDAO.update(session);
	}

	@Override
	public void deleteSession(String appKey, Session session) {
		sessionDAO.delete(session);
	}

	@Override
	public String[] getMappedValue(String appKey, String requestUri) {
		QueryWrapper<Permission> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(Permission::getType, EnumPermissionType.MENU_TYPE.value);
		List<Permission> list = permissionService.list(wrapper);
		for(Permission p : list) {
			if(pathMatcher.matches(p.getUri(), requestUri)) {
				return new String[] {p.getPermissionValue()};
			}
		}
		return null;
	}
}

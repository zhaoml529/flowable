package com.vk.flowable.shiro.context;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义基于url的权限过滤器
 */
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
	private String appKey;
	private ShiroUserContextService ctxService;
	
	public ShiroPermsFilter(String appKey,ShiroUserContextService ctxService) {
		this.appKey = appKey;
		this.ctxService = ctxService;
	}

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException{
		String requestURI = getPathWithinApplication(request);
		String[] perms = ctxService.getMappedValue(appKey, requestURI);
		
		Subject subject = getSubject(request, response);
		if(subject.hasRole("HandOfGod")) {	// 超级管理员
			return true;
		} else if(perms != null && perms.length > 0) {	// 有权限
			return super.isAccessAllowed(request, response, perms);
		}
		return false;
	}
}

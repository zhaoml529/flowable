package com.vk.flowable.shiro.client;

import com.vk.flowable.shiro.context.UserDto;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class ClientShiroUserRealm extends AuthorizingRealm {
	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserDto shiroUser = (UserDto) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(shiroUser.getRoleType());
		info.addStringPermissions(shiroUser.getPermissions());
		return info;
	}

	// 认证
	// 统一认证中心已经放在了flowable-admin中，所以下游系统依赖shiro-client端只进行鉴权
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		throw new UnsupportedOperationException("不会被调用");
	}
}
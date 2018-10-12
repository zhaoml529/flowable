package com.vk.flowable.shiro.context;

import org.apache.shiro.SecurityUtils;

public class ShiroUserContext{
	public static ShiroAdminUser getUserInfo() {
		return (ShiroAdminUser) SecurityUtils.getSubject().getPrincipal();
	}
}

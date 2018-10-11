package com.vk.flowable.shiro.context;

import org.apache.shiro.SecurityUtils;

public class ShiroUserContext{
	public static ShiroAdminUser getErpUser() {
		return (ShiroAdminUser) SecurityUtils.getSubject().getPrincipal();
	}
}

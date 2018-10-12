package com.vk.flowable.shiro.client;

import com.vk.flowable.shiro.context.ShiroAdminUser;
import org.apache.shiro.SecurityUtils;


public class ClientShiroUserContext{
	public static ShiroAdminUser getUserInfo() {
		return (ShiroAdminUser) SecurityUtils.getSubject().getPrincipal();
	}
}

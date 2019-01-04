package com.vk.flowable.shiro.context;

import org.apache.shiro.SecurityUtils;

/**
 * 客户端调用
 */
public class ShiroUserContext{
	public static UserDto getUserInfo() {
		return (UserDto) SecurityUtils.getSubject().getPrincipal();
	}
}

package com.vk.flowable.shiro.client;

import com.vk.flowable.shiro.context.UserDto;
import org.apache.shiro.SecurityUtils;


public class ClientShiroUserContext{
	public static UserDto getUserInfo() {
		return (UserDto) SecurityUtils.getSubject().getPrincipal();
	}
}

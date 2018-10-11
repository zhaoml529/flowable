package com.vk.flowable.shiro.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ShiroAdminUser implements Serializable{

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 登录名
	 */
	private String userName;

	/**
	 * 用户名
	 */
	private String realName;

	/**
	 * 用户类型枚举
	 */
	private EnumUserType userType;

	/**
	 * 用户登录的token
	 */
	private String token;

	private List<String> permissions = new ArrayList<String>();

	private List<String> roles = new ArrayList<String>();
	
	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return userName;
	}

	/**
	 * 重载hashCode,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(new String[] {userName});
	}

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShiroAdminUser other = (ShiroAdminUser) obj;
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public EnumUserType getUserType() {
		return userType;
	}

	public void setUserType(EnumUserType userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}

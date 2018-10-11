package com.vk.flowable.shiro.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:shiro-config.properties")
@ConfigurationProperties(prefix = "shiro.integration") //注入shiro.integration开头的属性
public class ShiroConfigProperties {
	private String appKey;
	private String loginUrl;
	private String successUrl;
	private String unauthorizedUrl;
	private String serviceUrl;
	private String cookieDomain;
	private String cookiePath;
	private String cookieName;
	private int cookieMaxAge = -1;
	private String filterConfig;
	private String filterChainConfig;

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public int getCookieMaxAge() {
		return cookieMaxAge;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(String filterConfig) {
		this.filterConfig = filterConfig;
	}

	public String getFilterChainConfig() {
		return filterChainConfig;
	}

	public void setFilterChainConfig(String filterChainConfig) {
		this.filterChainConfig = filterChainConfig;
	}
}

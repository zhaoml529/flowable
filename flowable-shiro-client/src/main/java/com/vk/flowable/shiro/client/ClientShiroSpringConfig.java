package com.vk.flowable.shiro.client;

import com.vk.flowable.shiro.context.ShiroConfigProperties;
import com.vk.flowable.shiro.context.ShiroPermsFilter;
import com.vk.flowable.shiro.context.ShiroUserContextService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientShiroSpringConfig {
	@Bean
	public SimpleCookie sessionIdCookie(ShiroConfigProperties prop) {
		SimpleCookie simpleCookie = new SimpleCookie(prop.getCookieName());
		simpleCookie.setHttpOnly(true);
		simpleCookie.setDomain(prop.getCookieDomain());
		simpleCookie.setPath(prop.getCookiePath());
		simpleCookie.setMaxAge(prop.getCookieMaxAge());
		return simpleCookie;
	}

	@Bean
	public ShiroUserContextService remoteContextService(ShiroConfigProperties prop) {
		HttpInvokerProxyFactoryBean bean = new HttpInvokerProxyFactoryBean();
		bean.setServiceUrl(prop.getServiceUrl());
		bean.setServiceInterface(ShiroUserContextService.class);
		bean.afterPropertiesSet();
		return (ShiroUserContextService) bean.getObject();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthorizingRealm clientRealm() {
		return new ClientShiroUserRealm();
	}

	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public SessionDAO sessionDAO(ShiroUserContextService ctxService) {
		ClientShiroSessionDAO dao = new ClientShiroSessionDAO();
		dao.setRemoteService(ctxService);
		return dao;
	}

	@Bean
	public SessionManager sessionManager(SessionDAO sessionDAO, SimpleCookie sessionIdCookie) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setDeleteInvalidSessions(false);
		sessionManager.setSessionValidationSchedulerEnabled(false);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		return sessionManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(SessionManager sessionManager, AuthorizingRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSessionManager(sessionManager);
		securityManager.setRealm(realm);
		return securityManager;
	}

	// 启用shiro注解
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}

    // Spring Aop proxy-target-class="true"
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	
	@Bean  
    public FilterRegistrationBean filterRegistrationBean() {  
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();  
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));   
        filterRegistration.setEnabled(true);  
        filterRegistration.addUrlPatterns("/*");   
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);  
        return filterRegistration;  
    }  
	
	@Bean(name = "clientAuthcFilter")
	public ClientShiroAuthcFilter clientAuthcFilter() {
		return new ClientShiroAuthcFilter();
	}
	
	@Bean(name = "clientPermsFilter")
	public ShiroPermsFilter clientPermsFilter(ShiroUserContextService ctxService,
											  ShiroConfigProperties prop) {
		ShiroPermsFilter filter = new ShiroPermsFilter(prop.getAppKey(), ctxService);
		return filter;
	}
	
	@Bean(name = "shiroFilter")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(ApplicationContext applicationContext,
                                                            SecurityManager securityManager, ShiroConfigProperties prop) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl(prop.getLoginUrl());
		shiroFilterFactoryBean.setSuccessUrl(prop.getSuccessUrl());
		shiroFilterFactoryBean.setUnauthorizedUrl(prop.getUnauthorizedUrl());

		// 设置过滤器
		if (StringUtils.hasText(prop.getFilterConfig())) {
			String[] filterArray = prop.getFilterConfig().split(";");
			for (String filter : filterArray) {
				String[] o = filter.split("=");
				shiroFilterFactoryBean.getFilters().put(o[0], (Filter) applicationContext.getBean(o[1]));
			}
		}

		// 设置url拦截
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		if (StringUtils.hasText(prop.getFilterChainConfig())) {
			String[] chainDefinitionsArray = prop.getFilterChainConfig().split(";");
			for (String filter : chainDefinitionsArray) {
				String[] o = filter.split("=");
				filterChainDefinitionMap.put(o[0], o[1]);
			}
		}
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}
	
}

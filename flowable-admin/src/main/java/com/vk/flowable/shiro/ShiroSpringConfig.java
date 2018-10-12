package com.vk.flowable.shiro;

import com.vk.flowable.shiro.context.ShiroConfigProperties;
import com.vk.flowable.shiro.context.ShiroPermsFilter;
import com.vk.flowable.shiro.context.ShiroUserContextService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroSpringConfig {

	// 会话Cookie模板
	@Bean
	public SimpleCookie sessionIdCookie(ShiroConfigProperties prop) {
		SimpleCookie simpleCookie = new SimpleCookie(prop.getCookieName());
		simpleCookie.setHttpOnly(true);		// 有助于减少跨站点脚本攻击
		simpleCookie.setDomain(prop.getCookieDomain());
		simpleCookie.setPath(prop.getCookiePath());
		simpleCookie.setMaxAge(prop.getCookieMaxAge()); // -1:关闭浏览器时过期cookie
		return simpleCookie;
	}

	// 缓存管理器
	@Bean
	public ShiroRedisCacheManager shiroCacheManager(RedisTemplate<String, Object> shiroRedisTemplate) {
		ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
		cacheManager.setRedisTemplate(shiroRedisTemplate);
		return cacheManager;
	}

	// 会话DAO 用于会话的CRUD
	@Bean
	public SessionDAO sessionDAO(RedisTemplate<String, Object> shiroRedisTemplate) {
		ShiroRedisSessionDAO sessionDAO = new ShiroRedisSessionDAO();
		sessionDAO.setRedisTemplate(shiroRedisTemplate);
		return sessionDAO;
	}

	@Bean
	public EnterpriseCacheSessionDAO sessionDAO() {
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		return sessionDAO;
	}


	@Bean(name = "/remote/ctx")
	public HttpInvokerServiceExporter authContextServiceExporter(AdminUserContextService shiroAuthContextService) {
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setServiceInterface(ShiroUserContextService.class);
		exporter.setService(shiroAuthContextService);
		return exporter;
	}

	// Realm实现
	@Bean
	public AuthorizingRealm getUserRealm() {
		return new AdminUserRealm();
	}

	// 会话管理器
	@Bean
	public SessionManager sessionManager(SessionDAO sessionDAO, SimpleCookie sessionIdCookie) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setDeleteInvalidSessions(true);				// 删除过期的会话
		sessionManager.setSessionValidationSchedulerEnabled(true);	// 开启会话验证调度器，调度时间间隔默认值为1小时
		// 是否启用Session Id Cookie,默认是启动；如果是禁用则默认使用servlet容器的 JSESSIONID
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		sessionManager.setGlobalSessionTimeout(2*60*60*1000);		// 设置全局会话超时时间 2小时
		return sessionManager;
	}

	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	// 安全管理器
	@Bean
	public DefaultWebSecurityManager securityManager(SessionManager sessionManager, CacheManager cacheManager,
                                                     Realm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(cacheManager);
		securityManager.setRealm(realm);
		return securityManager;
	}

	// 启用shiro权限注解
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

	// Shiro的Web过滤器
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager,
                                                            ShiroUserContextService userContextService) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");

		shiroFilterFactoryBean.getFilters().put("authc", new ShiroFormAuthenticationFilter());
		shiroFilterFactoryBean.getFilters().put("perms",new ShiroPermsFilter("erp", userContextService));

		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/assets/*", "anon");
		filterChainDefinitionMap.put("/remote/*", "anon");
		filterChainDefinitionMap.put("/unauthorized", "anon");
		filterChainDefinitionMap.put("/404", "authc");
		filterChainDefinitionMap.put("/home", "user");
		filterChainDefinitionMap.put("/*", "user,perms");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

}

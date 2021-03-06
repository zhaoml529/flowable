package com.vk.flowable.shiro;

import com.vk.flowable.domain.Permission;
import com.vk.flowable.domain.Role;
import com.vk.flowable.domain.User;
import com.vk.flowable.service.PermissionService;
import com.vk.flowable.service.RoleService;
import com.vk.flowable.service.UserService;
import com.vk.flowable.shiro.context.EnumUserType;
import com.vk.flowable.shiro.context.UserDto;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权中心
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 认证回调函数,登录时调用.
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getByUserName(token.getUsername());
		if(user == null) {
			return null;
		}
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		if(user.getUserType() == EnumUserType.SYS_USER.value) {
			dto.setRoleType("HandOfGod");	// 超级管理员
		} else {
			// 查询角色
			dto.setRoleType(getUserRoles(user.getRoleId()));
			// 查询权限
			dto.setPermissions(getUserPermissions(user.getRoleId()));
		}

		return new SimpleAuthenticationInfo(dto,user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserDto shiroUser = (UserDto) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(shiroUser.getUserType().value == EnumUserType.SYS_USER.value) {
			info.addRole("HandOfGod");
		}else {
			info.addRole(shiroUser.getRoleType());
			info.addStringPermissions(shiroUser.getPermissions());
		}
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SecurityUtils.HASH_ALGORITHM);
		matcher.setHashIterations(SecurityUtils.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	private String getUserRoles(Long userId) {
		Role role = roleService.getById(userId);
		return role.getType();
	}

	private List<String> getUserPermissions(Long roleId) {
		List<Permission>  permissionList = permissionService.rolePermissionList(roleId);
		List<String> permissions = permissionList.stream().map(entity -> entity.getPermissionValue()).collect(Collectors.toList());
		return permissions;
	}

}

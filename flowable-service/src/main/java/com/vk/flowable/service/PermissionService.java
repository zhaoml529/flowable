package com.vk.flowable.service;

import com.vk.flowable.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> rolePermissionList(Long roleId);

}

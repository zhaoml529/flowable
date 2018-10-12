package com.vk.flowable.service.impl;

import com.vk.flowable.domain.Permission;
import com.vk.flowable.mapper.PermissionMapper;
import com.vk.flowable.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> rolePermissionList(Long roleId) {
        List<Permission> list = this.rolePermissionList(roleId);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }
}

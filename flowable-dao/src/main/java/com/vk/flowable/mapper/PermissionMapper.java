package com.vk.flowable.mapper;

import com.vk.flowable.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> rolePermissionList(@Param("roleId") Long roleId);
}

package com.vk.flowable.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 1:系统 2:菜单 3:按钮
     */
    private Integer type;

    /**
     * 所属父级
     */
    private Long parentId;

    /**
     * 权限值
     */
    private String permissionValue;

    /**
     * 路径
     */
    private String uri;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

}

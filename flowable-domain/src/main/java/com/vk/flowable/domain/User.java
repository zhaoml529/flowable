package com.vk.flowable.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
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
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 实际姓名
     */
    private String realName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 获取盐
     * @return
     */
    public String getCredentialsSalt() {
        return userName + salt;
    }

}

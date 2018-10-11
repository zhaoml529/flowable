package com.vk.flowable.shiro.context;

/**
 * Created by zml on 2018/10/11.
 */
public enum EnumUserType {
    SYS_USER(1, "系统用户"),
    OTHER_USER(2, "其他用户");

    public final Integer value;

    public final String name;

    EnumUserType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static EnumUserType fromValue(Integer value) {
        for (EnumUserType type : EnumUserType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}

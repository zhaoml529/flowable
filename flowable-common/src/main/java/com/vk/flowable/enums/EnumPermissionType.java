package com.vk.flowable.enums;

/**
 * Created by zml on 2018/10/11.
 */
public enum EnumPermissionType {
    SYS_TYPE(1, "系统"),
    MENU_TYPE(2, "菜单"),
    FUNCTION_TYPE(3, "功能");

    public final Integer value;

    public final String name;

    EnumPermissionType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static EnumPermissionType fromValue(Integer value) {
        for (EnumPermissionType type : EnumPermissionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}

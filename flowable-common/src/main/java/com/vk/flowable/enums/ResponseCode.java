package com.vk.flowable.enums;

public enum ResponseCode {

    // 错误码只可新增或删除，不可修改
    ERROR("0001", "系统错误"),
    SUCCESS("0000", "操作成功"),
    LOGIN("7777", "重新登录");

    public final String code;

    public final String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

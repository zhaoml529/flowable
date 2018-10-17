package com.vk.flowable.exception;



import com.vk.flowable.enums.ResponseCode;

import java.io.Serializable;

public class BizException extends RuntimeException implements Serializable {

    private String code = ResponseCode.ERROR.code;
    private String msg;
    private Throwable e;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.e = e;
    }

    public BizException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
        this.e = e;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Throwable getE() {
        return this.e;
    }
}

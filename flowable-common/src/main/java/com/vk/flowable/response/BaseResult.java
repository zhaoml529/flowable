package com.vk.flowable.response;

import com.vk.flowable.enums.ResponseCode;

/**
 * 统一返回结果
 */
public class BaseResult {

    private String code;
    private String message;
    private Object data;
    private boolean success = Boolean.TRUE;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseResult() {
    }

    public BaseResult(String code, String result) {
        this.code = code;
        this.message = result;
    }

    public BaseResult(String code, String result, Object data) {
        this.code = code;
        this.message = result;
        this.data = data;
        if(!ResponseCode.SUCCESS.code.equals(code)) {
            this.success = Boolean.FALSE;
        }
    }

    public static BaseResult success() {
        return success("操作成功!");
    }

    public static BaseResult success(String msg) {
        return new BaseResult(ResponseCode.SUCCESS.code, msg, "success");
    }

    public static BaseResult success(Object obj) {
        if(obj==null){
            return new BaseResult(ResponseCode.SUCCESS.code,"success",null);
        }
        return new BaseResult(ResponseCode.SUCCESS.code,"success", obj);
    }

    public static BaseResult success(String msg, Object obj) {
        return new BaseResult(ResponseCode.SUCCESS.code, msg, obj);
    }

    public static BaseResult failure(String msg) {
        return new BaseResult(ResponseCode.ERROR.code, msg, null);
    }

    public static BaseResult failure(String code, String msg) {
        return new BaseResult(code, msg, null);
    }

    public static BaseResult warning() {
        return new BaseResult(ResponseCode.SUCCESS.code, "查无记录!");
    }

    public static BaseResult noDataEntity() {
        return new BaseResult(ResponseCode.SUCCESS.code, "查无记录!", "{}");
    }

    public static BaseResult noDataList() {
        return new BaseResult(ResponseCode.SUCCESS.code, "查无记录!", "[]");
    }
}

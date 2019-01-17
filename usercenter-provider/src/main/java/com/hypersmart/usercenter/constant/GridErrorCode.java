package com.hypersmart.usercenter.constant;


import com.hypersmart.framework.model.AppCode;

/**
 * <pre>
 * 作   者：fangyuan
 * 创建日期：2019-1-10
 * 网格管理错误编码
 * </pre>
 */
public enum GridErrorCode implements AppCode {

    RESOURCE_NOT_FOUND(404, "资源不存在"),
    UNKOWN_EXCEPTION(-1, "系统异常,请稍后重试！"),
    SUCCESS(0, "OK"),
    INSERT_EXCEPTION(10, "数据新增失败！"),
    INSERT_BATCH_EXCEPTION(11, "数据新增失败！"),
    INSERT_DUPLICATE(12, "该数据已存在！"),
    UPDATE_EXCEPTION(20, "数据更新失败！"),
    DELETE_EXCEPTION(30, "数据删除失败！"),
    DISABLE_EXCEPTION(31, "使数据无效失败！"),
    SELECT_ONE_EXCEPTION(40, "数据获取失败！"),
    SELECT_EXCEPTION(41, "数据获取失败！"),
    SELECT_PAGINATION_EXCEPTION(42, "数据获取失败！"),
    INVALID_SYSTEM_CLOCK(10101, "系统时间回调到当前时间之前的时间点，驳回产生ID%d毫秒"),
    UNKOWN_WORKER_ID(10102, "无法获取IdWorker标识"),
    INVALID_WORKER_ID(10103, "无效IdWorker标识，%d > %d"),
    PARAM_NOT_VALID(18500, "参数异常"),
    HAS_NO_PERMISSION(18501,"无权限访问"),
    ;


    private int code;
    private String message;

    private String format;

    private GridErrorCode(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
        this.format=message;
    }

    public GridErrorCode format(Object... msgArgs) {
        this.message =  String.format(this.format, msgArgs);
        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(getCode());
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the code
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    @Override
    public void setCode(int code) {
        this.code = code;
    }


}

package com.hypersmart.usercenter.util;


import com.hypersmart.framework.model.AppCode;

/**
 * <pre>
 * 作   者：fangyuan
 * 创建日期：2019-1-10
 * 网格管理错误编码
 * </pre>
 */
public enum ResourceErrorCode implements AppCode {
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
    PARAM_NOT_VALID(18500, "参数异常"),
    HAS_NO_PERMISSION(18501,"无权限访问"),
    EXIST_PARKING_NUM(20001,"车位编码已存在"),
    EXIST_PARKING_LOT_CODE(20002,"车场编码已存在"),
    EXIST_PARKING_SPACE(20003,"车场下存在车位信息，不允许删除"),
    EXPORT_EXCEPTION(20004,"导出异常！"),
    EMPTY_FILE(20005,"文件为空！"),
    IMPORT_EXCEPTION(20006,"导入失败！");

    private int code;
    private String message;

    private String format;

    private ResourceErrorCode(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
        this.format=message;
    }

    public ResourceErrorCode format(Object... msgArgs) {
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

package cn.lonelysnow.auth.config.enums;

import cn.lonelysnow.common.exception.BaseExceptionEnum;

/**
 * @author LonelySnow
 * @classname AuthExceptionEnum
 * @description 文件描述
 * @date 2022/9/19 15:30
 */
public enum AuthExceptionEnum implements BaseExceptionEnum {

    USER_LOGIN_ERROR(500, 100001, "用户名或密码错误");

    /**
     * http异常状态码
     */
    private Integer httpCode;

    /**
     * 自定义异常状态码
     */
    private Integer errCode;

    /**
     * 异常提示信息
     */
    private String message;

    AuthExceptionEnum(Integer httpCode, Integer errCode, String message) {
        this.httpCode = httpCode;
        this.errCode = errCode;
        this.message = message;
    }

    @Override
    public Integer getHttpCode() {
        return httpCode;
    }

    @Override
    public Integer getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

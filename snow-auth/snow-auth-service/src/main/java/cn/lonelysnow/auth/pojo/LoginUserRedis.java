package cn.lonelysnow.auth.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author LonelySnow
 * @classname LoginUserRedis
 * @description 文件描述
 * @date 2022/9/19 09:44
 */
@Data
public class LoginUserRedis {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态：0-禁用；1-启用
     */
    private Boolean status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别：0-未知；1-男；2-女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否删除：0-未删除；1-已删除
     */
    private Boolean deleted;

    /**
     * 角色信息
     */
    private List<String> roleName;

}

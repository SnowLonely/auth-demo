package cn.lonelysnow.auth.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @classname User
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Data
@TableName("auth_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-未删除；1-已删除
     */
    private Boolean deleted;

}

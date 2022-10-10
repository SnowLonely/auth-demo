package cn.lonelysnow.auth.pojo.dto;

import lombok.Data;

/**
 * @author LonelySnow
 * @classname LoginDTO
 * @description 文件描述
 * @date 2022/10/9 09:10
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}

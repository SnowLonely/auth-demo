package cn.lonelysnow.auth.service;

import cn.lonelysnow.auth.pojo.dto.LoginDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.lonelysnow.auth.pojo.User;

/**
 * @classname UserService
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户信息
     * @author LonelySnow
     * @param username
     * @return cn.lonelysnow.auth.pojo.User
     * @date 2022/9/16 17:00
     */
    User getUserByUsername(String username);

    /**
     * 用户登录
     * @author LonelySnow
     * @param loginDTO
     * @return java.lang.String
     * @date 2022/9/16 17:48
     */
    String login(LoginDTO loginDTO);
}

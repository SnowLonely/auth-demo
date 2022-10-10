package cn.lonelysnow.auth.service.impl;

import cn.lonelysnow.auth.pojo.LoginUser;
import cn.lonelysnow.auth.pojo.User;
import cn.lonelysnow.auth.service.RoleService;
import cn.lonelysnow.auth.service.UserService;
import cn.lonelysnow.common.utils.bean.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author LonelySnow
 * @classname UserDetailServiceImpl
 * @description 实现UserDetailsService，从数据库获取用户对象
 * @date 2022/9/16 15:55
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名获取数据库中的系统用户
        User user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            // throw new SnowException(AuthExceptionEnum.USER_LOGIN_ERROR);
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        LoginUser result = BeanHelper.copyProperties(user, LoginUser.class);

        // 查询用户具有的权限(名)
        List<String> roleName = roleService.getRoleMarkByUserId(user.getId());
        result.setRoleName(roleName);

        // 返回UserDetails
        return result;
    }

}

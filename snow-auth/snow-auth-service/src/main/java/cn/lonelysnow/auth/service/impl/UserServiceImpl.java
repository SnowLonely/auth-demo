package cn.lonelysnow.auth.service.impl;

import cn.lonelysnow.auth.config.enums.AuthExceptionEnum;
import cn.lonelysnow.auth.mapper.UserMapper;
import cn.lonelysnow.auth.pojo.LoginUser;
import cn.lonelysnow.auth.pojo.LoginUserRedis;
import cn.lonelysnow.auth.pojo.User;
import cn.lonelysnow.auth.pojo.dto.LoginDTO;
import cn.lonelysnow.auth.service.UserService;
import cn.lonelysnow.common.auth.JwtUtils;
import cn.lonelysnow.common.exception.SnowException;
import cn.lonelysnow.common.utils.bean.BeanHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @classname UserServiceImpl
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 使用数据库认证，导入已经注入的认证配置
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名查询用户信息
     */
    @Override
    public User getUserByUsername(String username) {
        // 拼接查询条件
        QueryWrapper<User> where = new QueryWrapper<>();
        where.lambda().eq(User::getUsername, username);
        // 查询
        User user = userMapper.selectOne(where);
        // 返回
        return user;
    }

    /**
     * 用户登录
     */
    @Override
    public String login(LoginDTO loginDTO) {
        // 使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果校验失败
        if (Objects.isNull(authenticate)) {
            throw new SnowException(AuthExceptionEnum.USER_LOGIN_ERROR);
        }

        // 提取登录用户信息
        LoginUser loginUser = (LoginUser) (authenticate.getPrincipal());

        // 生成Token
        Map<String, Object> data = new HashMap<>();
        data.put("id", loginUser.getId());
        data.put("name", loginUser.getNickName());
        String token = JwtUtils.generateTokenExpire(data);

        // 系统用户相关信息存放在redis中
        LoginUserRedis loginUserRedis = BeanHelper.copyProperties(loginUser, LoginUserRedis.class);
        redisTemplate.opsForValue().set("login:" + loginUser.getId(), loginUserRedis);

        return token;
    }
}

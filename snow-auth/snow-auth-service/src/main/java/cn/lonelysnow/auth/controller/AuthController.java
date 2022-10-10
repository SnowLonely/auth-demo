package cn.lonelysnow.auth.controller;

import cn.lonelysnow.auth.pojo.dto.LoginDTO;
import cn.lonelysnow.auth.service.UserService;
import cn.lonelysnow.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LonelySnow
 * @classname SystemController
 * @description 文件描述
 * @date 2022/9/11 17:26
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @author LonelySnow
     * @param loginDTO
     * @return cn.lonelysnow.common.result.Result<java.lang.String>
     * @date 2022/9/16 17:48
     */
    @PostMapping("/login")
    public Result<String> login(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return Result.success(token);
    }

}

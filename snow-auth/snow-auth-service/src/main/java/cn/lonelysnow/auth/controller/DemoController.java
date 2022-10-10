package cn.lonelysnow.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LonelySnow
 * @classname DemoController
 * @description 文件描述
 * @date 2022/9/9 14:58
 */
@RestController
public class DemoController {

    @GetMapping("/login")
    public void loginTr(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        response.setStatus(302);
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }

        response.setHeader("location","http://127.0.0.1:80");

        // 获取请求头中的cookie，重定向到自己的登录页面
        // Result.error(401, 100001, "请登录");
        // return Result.success("success");
    }

    @GetMapping("/demo")
    public String Demo() {
        return "Auto Demo Success";
    }

    @GetMapping("/")
    public String Demos() {
        return "No Path Success";
    }

}

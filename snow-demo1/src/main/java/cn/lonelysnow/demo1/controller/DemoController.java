package cn.lonelysnow.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LonelySnow
 * @classname DemoController
 * @description 文件描述
 * @date 2022/10/10 17:56
 */
@RestController
public class DemoController {

    @GetMapping("/")
    public String test() {
        return "Demo 1 Success";
    }

}

package cn.lonelysnow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LonelySnow
 * @classname AuthApplication
 * @description 文件描述
 * @date 2022/9/8 18:01
 */
@SpringBootApplication
@MapperScan("cn.lonelysnow.auth.mapper")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}

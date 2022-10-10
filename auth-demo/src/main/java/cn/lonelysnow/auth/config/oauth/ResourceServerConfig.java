package cn.lonelysnow.auth.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author LonelySnow
 * @classname ResourceServerConfig
 * @description 资源服务器
 * @date 2022/10/10 17:21
 */
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/userinfo/**").hasAuthority("SCOPE_userinfo")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}

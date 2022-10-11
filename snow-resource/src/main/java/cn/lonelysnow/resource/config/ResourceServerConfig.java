package cn.lonelysnow.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author LonelySnow
 * @classname ResourceServerConfig
 * @description 资源服务器
 * @date 2022/10/11 10:42
 */
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/messages/**")
                .authorizeRequests()
                .mvcMatchers("/messages/**").access("hasAuthority('SCOPE_message.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}

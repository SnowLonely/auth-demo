package cn.lonelysnow.auth.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author LonelySnow
 * @classname SecurityConfig
 * @description 文件描述
 * @date 2022/10/10 19:24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 用于身份验证安全过滤器链
     * <p><a href="https://docs.spring.io/spring-security/reference/servlet/authentication/index.html">用于身份验证安全过滤器链配置文档</a></p>
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**", "/login/**", "/logout/**", "/error").permitAll()
                .anyRequest().authenticated();

       //表单登录处理从授权服务器过滤器链
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }


    /**
     * 用于进行查询身份，验证的用户的UserDetailsService实例。
     * 详情查看spring-security的UserDetails
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

}

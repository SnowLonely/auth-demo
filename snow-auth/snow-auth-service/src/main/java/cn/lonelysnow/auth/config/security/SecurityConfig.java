package cn.lonelysnow.auth.config.security;

import cn.lonelysnow.auth.config.filter.JwtAuthenticationTokenFilter;
import cn.lonelysnow.auth.config.handler.AccessDeniedHandlerImpl;
import cn.lonelysnow.auth.config.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author LonelySnow
 * @classname SecurityConfig
 * @description Security配置文件
 * @date 2022/9/16 15:47
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 使用数据库验证时候需要导入该认证配置
     */
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 自定义JWT过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 自定义根据请求url分析请求所需的角色
     */
    @Autowired
    private CustomFilter customFilter;

    /**
     * 自定义判断用户角色
     */
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    /**
     * 自定义认证失败异常
     */
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * 自定义授权失败异常
     */
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 配置放行
     * @author LonelySnow
     * @param
     * @return org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
     * @date 2022/9/19 13:35
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers(
                        "/auth/login",
                        "/error",
                        "/oauth2/**",
                        "/.well-known/**",
                        "userinfo"
                );
    }

    /**
     * Security配置过滤器链+放行规则
     * @author LonelySnow
     * @param http
     * @return org.springframework.security.web.SecurityFilterChain
     * @date 2022/9/16 17:21
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();

        // 配置不通过Session获取SecurityContext(使用JWT)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置过滤放行
        http.authorizeRequests()
                // 动态配置访问权限
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                // 除配置放行的请求，全部需要及安全认证
                .anyRequest().authenticated();

        // 增加自定义过滤器，在某个过滤器之前(自定义JWT过滤器链存放在User过滤器之前)
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //
        // 告诉Security如何处理异常
        http.exceptionHandling()
                // 认证异常
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限异常
                .accessDeniedHandler(accessDeniedHandler);

        // 允许跨域强求
        http.cors();

        return http.build();
    }


    /**
     * 使用数据库验证时候需要注入认证配置
     * @author LonelySnow
     * @param
     * @return org.springframework.security.authentication.AuthenticationManager
     * @date 2022/9/16 15:49
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

    /**
     * 注入密码配置
     * @author LonelySnow
     * @param
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @date 2022/9/16 15:49
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package cn.lonelysnow.auth.config.filter;

import cn.lonelysnow.auth.pojo.LoginUser;
import cn.lonelysnow.auth.pojo.LoginUserRedis;
import cn.lonelysnow.common.auth.JwtUtils;
import cn.lonelysnow.common.utils.bean.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author LonelySnow
 * @classname JwtAuthenticationTokenFilter
 * @description 自定义过滤器，过滤JWT请求
 * @date 2022/9/16 17:42
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取Token
        String authToken = request.getHeader("token");
        // 判断是否存在
        if (StringUtils.hasText(authToken)) {
            // 获取Token中用户信息
            Map infoFromToken = JwtUtils.getInfoFromToken(authToken, Map.class);
            Integer id = (Integer) infoFromToken.get("id");
            String name = (String) infoFromToken.get("name");
            // token中存在用户名，但是未登录
            if (null != id && null == SecurityContextHolder.getContext().getAuthentication()) {
                // 登录(走登录方法获取用户属性)
                // UserDetails userDetails = userDetailsService.loadUserByUsername(name);

                // 从redis中获取数据
                LoginUserRedis loginUserRedis = (LoginUserRedis) redisTemplate.opsForValue().get("login:" + id);
                LoginUser userDetails = BeanHelper.copyProperties(loginUserRedis, LoginUser.class);

                // 封装Authentication-两参方法：未登录；三参方法：已登录 (用户信息，密码，权限)  userDetails.getAuthorities()
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 存入SecurityContextHolder(为了后续过滤器链可以使用)
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        // 放行，让后面的过滤器执行
        filterChain.doFilter(request, response);
    }

}

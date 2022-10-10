package cn.lonelysnow.auth.config.security;

import cn.lonelysnow.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author LonelySnow
 * @classname CustomFilter
 * @description 根据请求url分析请求所需的角色
 * @date 2022/9/19 10:42
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 从Redis中获取菜单需要的角色名称
        List<String> menuRoles = (List<String>) redisTemplate.opsForValue().get("menu:path:roles:" + requestUrl);
        // 判断是否为空，如果为空，从数据库中获取并存入Redis中
        if (Objects.isNull(menuRoles)) {
            menuRoles = roleService.getRolesByMenu(requestUrl);
            redisTemplate.opsForValue().set("menu:path:roles:" + requestUrl, menuRoles);
            // 如果数据库中未定义该路径，则设定默认登录访问
            if (Objects.isNull(menuRoles)) {
                return SecurityConfig.createList("ROLE_LOGIN");
            }
        }
        String[] strings = menuRoles.stream().toArray(String[]::new);
        return SecurityConfig.createList(strings);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}

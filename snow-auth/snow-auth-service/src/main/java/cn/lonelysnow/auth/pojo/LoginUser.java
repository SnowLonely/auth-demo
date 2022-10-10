package cn.lonelysnow.auth.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LonelySnow
 * @classname LoginUser
 * @description 文件描述
 * @date 2022/9/16 15:50
 */
@Data
public class LoginUser implements UserDetails {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态：0-禁用；1-启用
     */
    private Boolean status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别：0-未知；1-男；2-女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否删除：0-未删除；1-已删除
     */
    private Boolean deleted;

    /**
     * 角色信息
     */
    private List<String> roleName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = roleName.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return !deleted;
    }

    /**
     * 帐户未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 已启用
     */
    @Override
    public boolean isEnabled() {
        return status;
    }

}

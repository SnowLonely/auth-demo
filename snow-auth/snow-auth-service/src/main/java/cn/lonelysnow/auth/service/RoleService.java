package cn.lonelysnow.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.lonelysnow.auth.pojo.Role;

import java.util.List;

/**
 * @classname RoleService
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询用户具有的角色信息
     * @author LonelySnow
     * @param id
     * @return java.util.List<java.lang.String>
     * @date 2022/9/16 17:05
     */
    List<String> getRoleMarkByUserId(Integer id);

    /**
     * 根据路由获取可以访问的角色角色信息
     * @author LonelySnow
     * @param requestUrl
     * @return java.util.List<java.lang.String>
     * @date 2022/9/19 12:52
     */
    List<String> getRolesByMenu(String requestUrl);
}

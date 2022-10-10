package cn.lonelysnow.auth.service.impl;

import cn.lonelysnow.auth.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lonelysnow.auth.mapper.RoleMapper;
import cn.lonelysnow.auth.service.RoleService;
import cn.lonelysnow.auth.pojo.Role;

import java.util.List;

/**
 * @classname RoleServiceImpl
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuService menuService;

    /**
     * 根据用户id查询用户具有的角色信息
     */
    @Override
    public List<String> getRoleMarkByUserId(Integer id) {
        // 查询用户具有的角色名
        List<String> result = roleMapper.getRoleMarkByUserId(id);
        // 返回
        return result;
    }

    /**
     * 根据路由获取可以访问的角色角色信息
     */
    @Override
    public List<String> getRolesByMenu(String requestUrl) {
        // 根据菜单path获取id
        Integer menuId = menuService.getIdByPath(requestUrl);

        // 根据id获取对应的角色名称
        List<String> rolesName = roleMapper.getRoleNamesByMenuId(menuId);

        return rolesName;
    }
}

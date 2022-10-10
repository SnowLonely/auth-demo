package cn.lonelysnow.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.lonelysnow.auth.pojo.Role;

import java.util.List;

/**
 * @classname RoleMapper
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户具有的角色信息
     * @author LonelySnow
     * @param id
     * @return java.util.List<java.lang.String>
     * @date 2022/9/16 17:08
     */
    List<String> getRoleMarkByUserId(Integer id);

    /**
     * 根据菜单id查询可以访问的角色
     * @author LonelySnow
     * @param menuId
     * @return java.util.List<java.lang.String>
     * @date 2022/9/19 13:10
     */
    List<String> getRoleNamesByMenuId(Integer menuId);
}

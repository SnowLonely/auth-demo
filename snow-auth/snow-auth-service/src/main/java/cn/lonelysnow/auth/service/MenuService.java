package cn.lonelysnow.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.lonelysnow.auth.pojo.Menu;

/**
 * @classname MenuService
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据菜单path获取id
     * @author LonelySnow
     * @param requestUrl
     * @return java.lang.Integer
     * @date 2022/9/19 13:05
     */
    Integer getIdByPath(String requestUrl);
}

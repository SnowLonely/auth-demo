package cn.lonelysnow.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lonelysnow.auth.mapper.MenuMapper;
import cn.lonelysnow.auth.service.MenuService;
import cn.lonelysnow.auth.pojo.Menu;

import java.util.Objects;

/**
 * @classname MenuServiceImpl
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据菜单path获取id
     */
    @Override
    public Integer getIdByPath(String requestUrl) {
        QueryWrapper<Menu> where = new QueryWrapper<>();
        where.lambda().select(Menu::getId);
        where.lambda().eq(Menu::getPath, requestUrl);

        Menu menu = menuMapper.selectOne(where);

        if (Objects.isNull(menu)) {
            return null;
        }

        return menu.getId();
    }
}

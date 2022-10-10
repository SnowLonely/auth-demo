package cn.lonelysnow.auth.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @classname Menu
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Data
@TableName("auth_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单标识(英文名称)
     */
    private String mark;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否展示：0-隐藏；1-展示
     */
    private Boolean visible;

    /**
     * 菜单状态：0-禁用；1-启用
     */
    private Boolean status;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-未删除；1-已删除
     */
    private Boolean deleted;

}

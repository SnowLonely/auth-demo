package cn.lonelysnow.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.lonelysnow.auth.pojo.User;

/**
 * @classname UserMapper
 * @description 文件描述
 * @author LonelySnow
 * @date 2022-09-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}

package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户表用户积分增加
     *
     * @param userId
     * @param addIntegral
     */
    void addIntegral(@Param("userId") Long userId, @Param("addIntegral") Integer addIntegral);

    /**
     * 减去用户积分
     *
     * @param userId
     * @param subtractIntegral
     */
    void subtractIntegral(@Param("userId") Long userId, @Param("subtractIntegral") Long subtractIntegral);
}

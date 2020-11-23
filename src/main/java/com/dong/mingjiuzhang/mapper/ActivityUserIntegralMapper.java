package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegral;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户活动所得积分表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityUserIntegralMapper extends BaseMapper<ActivityUserIntegral> {

    /**
     * 增加活动积分
     *
     * @param activityId
     * @param userId
     * @param addIntegral
     */
    void addIntegral(@Param("activityId") Long activityId, @Param("userId") Long userId, @Param("addIntegral") Integer addIntegral);
}

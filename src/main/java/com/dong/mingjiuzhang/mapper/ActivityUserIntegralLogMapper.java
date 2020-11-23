package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegralLog;
import com.dong.mingjiuzhang.domain.entity.vo.WrongActivityRequestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户活动所得积分表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityUserIntegralLogMapper extends BaseMapper<ActivityUserIntegralLog> {

    /**
     * 我的活动错题
     *
     * @param userId
     * @param activityId
     * @return
     */
    List<WrongActivityRequestVO> wrongActivityRequest(@Param("userId") Long userId, @Param("activityId") Long activityId);
}

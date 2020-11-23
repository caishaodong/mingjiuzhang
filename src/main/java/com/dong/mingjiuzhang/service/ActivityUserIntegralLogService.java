package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegralLog;
import com.dong.mingjiuzhang.domain.entity.vo.WrongActivityRequestVO;

import java.util.List;

/**
 * <p>
 * 用户活动所得积分表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityUserIntegralLogService extends IService<ActivityUserIntegralLog> {

    /**
     * 我的活动错题
     *
     * @param userId
     * @param activityId
     * @return
     */
    List<WrongActivityRequestVO> wrongActivityRequest(Long userId, Long activityId);
}

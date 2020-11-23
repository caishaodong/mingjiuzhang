package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegral;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ActivityAnswerRequestDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityRankVO;

/**
 * <p>
 * 用户活动所得积分表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityUserIntegralService extends IService<ActivityUserIntegral> {

    /**
     * 获取活动积分排行榜
     *
     * @param userId
     * @param activityId
     * @return
     */
    ActivityRankVO rankList(Long userId, Long activityId);

    /**
     * 回答问题
     *
     * @param user
     * @param activityAnswerRequestDTO
     */
    void answerRequest(User user, ActivityAnswerRequestDTO activityAnswerRequestDTO);
}

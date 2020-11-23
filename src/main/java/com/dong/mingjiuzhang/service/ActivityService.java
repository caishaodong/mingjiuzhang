package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Activity;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityUserVO;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityService extends IService<Activity> {

    Activity getOkById(Long id);

    /**
     * 获取百日无错题活动信息
     *
     * @return
     */
    Activity info();

    /**
     * 获取我的信息
     *
     * @param userId
     * @param activityId
     * @return
     */
    ActivityUserVO getMyInfo(Long userId, Long activityId);
}

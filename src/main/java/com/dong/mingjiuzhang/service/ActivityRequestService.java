package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.ActivityRequest;

import java.util.List;

/**
 * <p>
 * 活动题目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface ActivityRequestService extends IService<ActivityRequest> {
    ActivityRequest getOkById(Long id);

    /**
     * 前日解析
     *
     * @param grade
     * @param activityId
     * @return
     */
    List<ActivityRequest> yesterdayRequestAnalysis(String grade, Long activityId);

    /**
     * 获取今日题目
     *
     * @param grade
     * @param activityId
     * @return
     */
    List<ActivityRequest> todayRequest(String grade, Long activityId);
}

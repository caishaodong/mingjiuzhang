package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.ActivityRequest;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.mapper.ActivityRequestMapper;
import com.dong.mingjiuzhang.service.ActivityRequestService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 活动题目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class ActivityRequestServiceImpl extends ServiceImpl<ActivityRequestMapper, ActivityRequest> implements ActivityRequestService {

    @Override
    public ActivityRequest getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<ActivityRequest>().eq(ActivityRequest::getId, id).eq(ActivityRequest::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 前日解析
     *
     * @param grade
     * @param activityId
     * @return
     */
    @Override
    public List<ActivityRequest> yesterdayRequestAnalysis(String grade, Long activityId) {
        LocalDateTime yesterday = LocalDateTime.now().plusDays(-1);
        List<ActivityRequest> list = this.list(new LambdaQueryWrapper<ActivityRequest>()
                .eq(ActivityRequest::getGrade, grade)
                .eq(ActivityRequest::getActivityId, activityId)
                .lt(ActivityRequest::getGmtStartEffect, yesterday)
                .gt(ActivityRequest::getGmtEndEffect, yesterday));
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(BusinessEnum.NO_YESTERDAY_REQUEST_ANALYSIS);
        }
        return list;
    }

    /**
     * 获取今日题目
     *
     * @param grade
     * @param activityId
     * @return
     */
    @Override
    public List<ActivityRequest> todayRequest(String grade, Long activityId) {
        LocalDateTime yesterday = LocalDateTime.now();
        List<ActivityRequest> list = this.list(new LambdaQueryWrapper<ActivityRequest>()
                .eq(ActivityRequest::getGrade, grade)
                .eq(ActivityRequest::getActivityId, activityId)
                .lt(ActivityRequest::getGmtStartEffect, yesterday)
                .gt(ActivityRequest::getGmtEndEffect, yesterday));
        return list;
    }
}

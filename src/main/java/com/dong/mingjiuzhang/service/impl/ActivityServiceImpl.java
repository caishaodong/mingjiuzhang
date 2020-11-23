package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Activity;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegral;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegralLog;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityUserVO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.ActivityMapper;
import com.dong.mingjiuzhang.service.ActivityService;
import com.dong.mingjiuzhang.service.ActivityUserIntegralLogService;
import com.dong.mingjiuzhang.service.ActivityUserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private ActivityUserIntegralLogService activityUserIntegralLogService;
    @Autowired
    private ActivityUserIntegralService activityUserIntegralService;

    @Override
    public Activity getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Activity>().eq(Activity::getId, id).eq(Activity::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取百日无错题活动信息
     *
     * @return
     */
    @Override
    public Activity info() {
        LocalDateTime now = LocalDateTime.now();
        List<Activity> list = this.list(new LambdaQueryWrapper<Activity>().eq(Activity::getIsDeleted, YesNoEnum.NO.getValue())
                .le(Activity::getGmtStartEffect, now)
                .ge(Activity::getGmtEndEffect, now)
                .orderByDesc(Activity::getId));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 获取我的信息
     *
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ActivityUserVO getMyInfo(Long userId, Long activityId) {
        // 用户所得积分
        ActivityUserIntegral activityUserIntegral = activityUserIntegralService.getOne(new LambdaQueryWrapper<ActivityUserIntegral>()
                .eq(ActivityUserIntegral::getActivityId, activityId)
                .eq(ActivityUserIntegral::getUserId, userId)
                .eq(ActivityUserIntegral::getIsDeleted, YesNoEnum.NO.getValue()));

        // 用户答题次数
        QueryWrapper<ActivityUserIntegralLog> answerCountQueryMapper = new QueryWrapper<>();
        answerCountQueryMapper.select("COUNT(id) as answerCount")
                .eq("activity_id", activityId)
                .eq("user_id", userId)
                .eq("is_deleted", YesNoEnum.NO.getValue());
        Map<String, Object> answerCountMap = activityUserIntegralLogService.getMap(answerCountQueryMapper);
        Integer answerCount = (Integer) answerCountMap.get("answerCount");

        ActivityUserVO activityUserVO = new ActivityUserVO();
        activityUserVO.setTotalIntegral(Objects.isNull(activityUserIntegral) ? 0 : activityUserIntegral.getTotalIntegral());
        activityUserVO.setAnswerCount(answerCount);
        return activityUserVO;
    }
}

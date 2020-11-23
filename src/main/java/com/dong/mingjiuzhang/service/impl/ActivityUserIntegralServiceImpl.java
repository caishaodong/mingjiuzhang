package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.ActivityRequest;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegral;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegralLog;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ActivityAnswerRequestDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityRankVO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.ActivityUserIntegralMapper;
import com.dong.mingjiuzhang.service.ActivityRequestService;
import com.dong.mingjiuzhang.service.ActivityUserIntegralLogService;
import com.dong.mingjiuzhang.service.ActivityUserIntegralService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户活动所得积分表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class ActivityUserIntegralServiceImpl extends ServiceImpl<ActivityUserIntegralMapper, ActivityUserIntegral> implements ActivityUserIntegralService {

    @Autowired
    private ActivityUserIntegralService activityUserIntegralService;
    @Autowired
    private ActivityUserIntegralLogService activityUserIntegralLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityRequestService activityRequestService;

    /**
     * 获取活动积分排行榜
     *
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ActivityRankVO rankList(Long userId, Long activityId) {

        // 获取排行榜
        List<ActivityUserIntegral> list = this.list(new LambdaQueryWrapper<ActivityUserIntegral>()
                .eq(ActivityUserIntegral::getActivityId, activityId)
                .eq(ActivityUserIntegral::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(ActivityUserIntegral::getTotalIntegral));

        // 我是否参与过答题
        ActivityUserIntegral activityUserIntegral = this.getOne(new LambdaQueryWrapper<ActivityUserIntegral>().eq(ActivityUserIntegral::getActivityId, activityId)
                .eq(ActivityUserIntegral::getUserId, userId)
                .eq(ActivityUserIntegral::getIsDeleted, YesNoEnum.YES.getValue()));

        int rankIndex = 0;
        if (Objects.nonNull(activityUserIntegral)) {
            // 查看我的排名
            rankIndex = list.stream().map(ActivityUserIntegral::getId).collect(Collectors.toList()).indexOf(activityUserIntegral.getId());
        }

        ActivityRankVO activityRankVO = new ActivityRankVO();
        activityRankVO.setList(list);
        activityRankVO.setActivityUserIntegral(activityUserIntegral);
        activityRankVO.setIsAnswered(Objects.isNull(activityUserIntegral) ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue());
        activityRankVO.setRandIndex(rankIndex > 0 ? rankIndex : null);
        return null;
    }

    /**
     * 回答问题
     *
     * @param user
     * @param activityAnswerRequestDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void answerRequest(User user, ActivityAnswerRequestDTO activityAnswerRequestDTO) {
        ActivityRequest activityRequest = activityRequestService.getOkById(activityAnswerRequestDTO.getActivityRequestId());
        Integer addIntegral = Objects.equals(activityAnswerRequestDTO.getIsCorrect(), YesNoEnum.YES.getValue()) ?
                ((Objects.isNull(activityRequest.getIntegral()) || Objects.equals(activityRequest.getIntegral(), 0)) ? 50 : activityRequest.getIntegral())
                : 0;
        // 判断用户是否参加过活动，没有的话，增加记录和积分，有的话，增加积分
        ActivityUserIntegral activityUserIntegral = activityUserIntegralService.getOne(new LambdaQueryWrapper<ActivityUserIntegral>()
                .eq(ActivityUserIntegral::getUserId, user.getId())
                .eq(ActivityUserIntegral::getActivityId, activityAnswerRequestDTO.getActivityId())
                .eq(ActivityUserIntegral::getIsDeleted, YesNoEnum.NO.getValue()));
        if (Objects.isNull(activityUserIntegral)) {
            // 添加新记录
            activityUserIntegral = new ActivityUserIntegral();
            activityUserIntegral.setActivityId(activityAnswerRequestDTO.getActivityId());
            activityUserIntegral.setUserId(user.getId());
            activityUserIntegral.setUsername(user.getUsername());
            activityUserIntegral.setTotalIntegral(addIntegral);
            ReflectUtil.setCreateInfo(activityUserIntegral, ActivityUserIntegral.class);
            this.save(activityUserIntegral);
        } else {
            // 增加活动积分
            this.baseMapper.addIntegral(activityAnswerRequestDTO.getActivityId(), user.getId(), addIntegral);
        }

        // 增加用户答题积分记录
        ActivityUserIntegralLog activityUserIntegralLog = new ActivityUserIntegralLog();
        activityUserIntegralLog.setActivityId(activityAnswerRequestDTO.getActivityId());
        activityUserIntegralLog.setUserId(user.getId());
        activityUserIntegralLog.setActivityRequestId(activityAnswerRequestDTO.getActivityRequestId());
        activityUserIntegralLog.setIsCorrect(activityAnswerRequestDTO.getIsCorrect());
        activityUserIntegralLog.setIntegral(addIntegral);
        ReflectUtil.setCreateInfo(activityUserIntegralLog, ActivityUserIntegralLog.class);
        activityUserIntegralLogService.save(activityUserIntegralLog);
        // 用户表用户积分增加
        userService.addIntegral(user.getId(), addIntegral);
    }
}

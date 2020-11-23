package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Activity;
import com.dong.mingjiuzhang.domain.entity.ActivityRequest;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ActivityAnswerRequestDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityRankVO;
import com.dong.mingjiuzhang.domain.entity.vo.ActivityUserVO;
import com.dong.mingjiuzhang.domain.entity.vo.WrongActivityRequestVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * (API)活动
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityApiController extends BaseController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityUserIntegralService activityUserIntegralService;
    @Autowired
    private ActivityUserIntegralLogService activityUserIntegralLogService;
    @Autowired
    private ActivityRequestService activityRequestService;

    /**
     * 获取百日无错题活动信息
     *
     * @return
     */
    @GetMapping("/info")
    public ResponseResult<Activity> info() {
        Activity activity = activityService.info();
        return success(activity);
    }

    /**
     * 获取我的信息
     *
     * @param request
     * @param activityId 活动id
     * @return
     */
    @GetMapping("/{activityId}/myInfo")
    public ResponseResult<ActivityUserVO> myInfo(HttpServletRequest request, @PathVariable("activityId") Long activityId) {
        // 校验活动是否存在
        activityCheck(activityId);

        User user = userService.getUserByToken(request);
        ActivityUserVO activityUserVO = activityService.getMyInfo(user.getId(), activityId);
        return success(activityUserVO);
    }

    /**
     * 获取活动积分排行榜
     *
     * @param request
     * @param activityId 活动id
     * @return
     */
    @GetMapping("/{activityId}/rank")
    public ResponseResult<ActivityRankVO> rankList(HttpServletRequest request, @PathVariable("activityId") Long activityId) {
        // 校验活动是否存在
        activityCheck(activityId);

        User user = userService.getUserByToken(request);
        ActivityRankVO activityRankVO = activityUserIntegralService.rankList(user.getId(), activityId);
        return success(activityRankVO);
    }

    /**
     * 我的活动错题
     *
     * @param request
     * @param activityId 活动id
     * @return
     */
    @GetMapping("/{activityId}/wrongActivityRequest")
    public ResponseResult<List<WrongActivityRequestVO>> wrongActivityRequest(HttpServletRequest request, @PathVariable("activityId") Long activityId) {
        // 校验活动是否存在
        activityCheck(activityId);

        User user = userService.getUserByToken(request);
        List<WrongActivityRequestVO> list = activityUserIntegralLogService.wrongActivityRequest(user.getId(), activityId);
        return success(list);
    }

    /**
     * 前日解析
     *
     * @param request
     * @param activityId 活动id
     * @return
     */
    @GetMapping("/{activityId}/yesterdayRequestAnalysis")
    public ResponseResult<List<ActivityRequest>> yesterdayRequestAnalysis(HttpServletRequest request, @PathVariable("activityId") Long activityId) {
        User user = userService.getUserByToken(request);
        List<ActivityRequest> list = activityRequestService.yesterdayRequestAnalysis(user.getGrade(), activityId);
        return success(list);

    }

    /**
     * 获取今日题目
     *
     * @param request
     * @param activityId
     * @return
     */
    @GetMapping("/{activityId}/todayRequest")
    public ResponseResult<List<ActivityRequest>> todayRequest(HttpServletRequest request, @PathVariable("activityId") Long activityId) {
        User user = userService.getUserByToken(request);
        List<ActivityRequest> list = activityRequestService.todayRequest(user.getGrade(), activityId);
        return success(list);

    }

    /**
     * 回答问题
     *
     * @param request
     * @param activityAnswerRequestDTO
     * @return
     */
    @GetMapping("/answerRequest")
    public ResponseResult answerRequest(HttpServletRequest request, @RequestBody ActivityAnswerRequestDTO activityAnswerRequestDTO) {
        // 参数校验
        activityAnswerRequestDTO.paramCheck();
        User user = userService.getUserByToken(request);

        LocalDateTime now = LocalDateTime.now();
        // 校验活动存在
        Activity activity = activityService.getOkById(activityAnswerRequestDTO.getActivityId());
        if (Objects.isNull(activity)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 校验活动是否在有效期内
        if (activity.getGmtStartEffect().isAfter(now)) {
            // 活动尚未开始
            throw new BusinessException(BusinessEnum.NOT_START_ACTIVITY);
        }
        if (activity.getGmtEndEffect().isBefore(now)) {
            // 活动已经结束
            throw new BusinessException(BusinessEnum.ALREADY_END_ACTIVITY);
        }
        // 校验题目是否存在
        ActivityRequest activityRequest = activityRequestService.getOkById(activityAnswerRequestDTO.getActivityRequestId());
        if (Objects.isNull(activityRequest)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 校验题目是否在有效期内
        if (activityRequest.getGmtStartEffect().isAfter(now)) {
            // 活动尚未开始
            throw new BusinessException(BusinessEnum.NOT_START_ACTIVITY);
        }
        if (activityRequest.getGmtEndEffect().isBefore(now)) {
            // 活动已经结束
            throw new BusinessException(BusinessEnum.ALREADY_END_ACTIVITY);
        }
        // 校验题目和活动的匹配性
        if (!Objects.equals(activityRequest.getActivityId(), activityAnswerRequestDTO.getActivityId())) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 回答问题
        activityUserIntegralService.answerRequest(user, activityAnswerRequestDTO);
        return success();

    }


    /**
     * 校验活动是否存在
     *
     * @param activityId
     */
    public void activityCheck(Long activityId) {
        Activity activity = activityService.getOkById(activityId);
        if (Objects.isNull(activity)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }

}

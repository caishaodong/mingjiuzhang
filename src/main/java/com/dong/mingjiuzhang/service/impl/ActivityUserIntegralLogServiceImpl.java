package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegralLog;
import com.dong.mingjiuzhang.domain.entity.vo.WrongActivityRequestVO;
import com.dong.mingjiuzhang.mapper.ActivityUserIntegralLogMapper;
import com.dong.mingjiuzhang.service.ActivityUserIntegralLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户活动所得积分表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class ActivityUserIntegralLogServiceImpl extends ServiceImpl<ActivityUserIntegralLogMapper, ActivityUserIntegralLog> implements ActivityUserIntegralLogService {

    /**
     * 我的活动错题
     *
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public List<WrongActivityRequestVO> wrongActivityRequest(Long userId, Long activityId) {
        return this.baseMapper.wrongActivityRequest(userId, activityId);
    }
}

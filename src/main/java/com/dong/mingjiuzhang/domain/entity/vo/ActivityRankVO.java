package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.ActivityUserIntegral;
import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-11-23 14:54
 * @Description
 **/
@Data
public class ActivityRankVO {
    /**
     * 我是否参与过答题：0否 1是
     */
    private Integer isAnswered;

    /**
     * 我的排名
     */
    private Integer randIndex;

    /**
     * 我的积分信息
     */
    private ActivityUserIntegral activityUserIntegral;

    /**
     * 排行榜
     */
    private List<ActivityUserIntegral> list;
}

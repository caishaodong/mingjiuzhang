package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.Problem;
import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-11-22 19:58
 * @Description
 **/
@Data
public class ProblemVO extends Problem {
    /**
     * 用户名
     */
    private String username;
    /**
     * 图像url
     */
    private String avatar;
    /**
     * 问题回复列表
     */
    private List<ProblemReplyVO> problemReplyVOList;
}

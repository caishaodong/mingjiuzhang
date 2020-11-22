package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.ProblemReply;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemReplyVO;

import java.util.List;

/**
 * <p>
 * 问题回复表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface ProblemReplyService extends IService<ProblemReply> {

    /**
     * 根据问题id获取回复列表
     *
     * @param problemId
     * @return
     */
    List<ProblemReplyVO> getListByProblemId(Long problemId);
}

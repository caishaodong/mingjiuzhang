package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.ProblemReply;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemReplyVO;
import com.dong.mingjiuzhang.mapper.ProblemReplyMapper;
import com.dong.mingjiuzhang.service.ProblemReplyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题回复表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class ProblemReplyServiceImpl extends ServiceImpl<ProblemReplyMapper, ProblemReply> implements ProblemReplyService {

    /**
     * 根据问题id获取回复列表
     *
     * @param problemId
     * @return
     */
    @Override
    public List<ProblemReplyVO> getListByProblemId(Long problemId) {
        return this.baseMapper.getListByProblemId(problemId);
    }
}

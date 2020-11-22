package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.ProblemReply;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 问题回复表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface ProblemReplyMapper extends BaseMapper<ProblemReply> {

    /**
     * 根据问题id获取回复列表
     *
     * @param problemId
     * @return
     */
    List<ProblemReplyVO> getListByProblemId(@Param("problemId") Long problemId);
}

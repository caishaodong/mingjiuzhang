package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.AnswerLog;
import com.dong.mingjiuzhang.domain.entity.vo.WrongCollectionVO;

import java.util.List;

/**
 * <p>
 * 答题记录表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface AnswerLogMapper extends BaseMapper<AnswerLog> {

    /**
     * 错题集
     *
     * @param userId
     * @return
     */
    List<WrongCollectionVO> wrongCollectionList(Long userId);
}

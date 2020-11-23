package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.AnswerLog;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerLogSaveDTO;
import com.dong.mingjiuzhang.domain.entity.vo.WrongCollectionVO;

import java.util.List;

/**
 * <p>
 * 答题记录表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface AnswerLogService extends IService<AnswerLog> {

    /**
     * 保存答题记录
     *
     * @param userId
     * @param answerLogSaveDTO
     */
    void save(Long userId, AnswerLogSaveDTO answerLogSaveDTO);

    /**
     * 错题集
     *
     * @param userId
     * @return
     */
    List<WrongCollectionVO> wrongCollectionList(Long userId);
}

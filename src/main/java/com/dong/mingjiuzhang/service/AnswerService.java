package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Answer;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerSaveDTO;

/**
 * <p>
 * 答题表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface AnswerService extends IService<Answer> {

    Answer getOkById(Long id);

    /**
     * 保存答题
     *
     * @param user
     * @param answerSaveDTO
     */
    void save(User user, AnswerSaveDTO answerSaveDTO);
}

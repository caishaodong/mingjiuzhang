package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Answer;
import com.dong.mingjiuzhang.domain.entity.AnswerLog;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerLogSaveDTO;
import com.dong.mingjiuzhang.domain.entity.vo.WrongCollectionVO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.AnswerLogMapper;
import com.dong.mingjiuzhang.service.AnswerLogService;
import com.dong.mingjiuzhang.service.AnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 答题记录表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class AnswerLogServiceImpl extends ServiceImpl<AnswerLogMapper, AnswerLog> implements AnswerLogService {
    @Autowired
    private AnswerService answerService;

    /**
     * 保存答题记录
     *
     * @param userId
     * @param answerLogSaveDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long userId, AnswerLogSaveDTO answerLogSaveDTO) {
        AnswerLog answerLog = new AnswerLog();
        BeanUtils.copyProperties(answerLogSaveDTO, answerLog);
        ReflectUtil.setCreateInfo(answerLog, AnswerLog.class);
        // 保存答题记录
        answerLog.setUserId(userId);
        this.save(answerLog);

        // 校验是否答题完毕
        if (Objects.equals(answerLogSaveDTO.getIsFinishAnswer(), YesNoEnum.YES.getValue())) {
            Answer answer = new Answer();
            answer.setId(answerLogSaveDTO.getAnswerId());
            answer.setStatus(2);
            answer.setAnswerDuration(answerLogSaveDTO.getAnswerDuration());
            // 修改答题信息
            answerService.updateById(answer);
        }
    }

    /**
     * 错题集
     *
     * @param userId
     * @return
     */
    @Override
    public List<WrongCollectionVO> wrongCollectionList(Long userId) {
        return this.baseMapper.wrongCollectionList(userId);
    }
}

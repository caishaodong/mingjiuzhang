package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Answer;
import com.dong.mingjiuzhang.domain.entity.SubjectCate;
import com.dong.mingjiuzhang.domain.entity.SubjectSecondDict;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerSaveDTO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.AnswerMapper;
import com.dong.mingjiuzhang.service.AnswerService;
import com.dong.mingjiuzhang.service.SubjectCateService;
import com.dong.mingjiuzhang.service.SubjectSecondDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 答题表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    @Autowired
    private SubjectCateService subjectCateService;
    @Autowired
    private SubjectSecondDictService subjectSecondDictService;

    @Override
    public Answer getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Answer>().eq(Answer::getId, id).eq(Answer::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 保存答题
     *
     * @param user
     * @param answerSaveDTO
     */
    @Override
    public void save(User user, AnswerSaveDTO answerSaveDTO) {
        Answer answer = new Answer();
        if (Objects.nonNull(answerSaveDTO.getSubjectCateId())) {
            // 获取题目所属科目信息
            SubjectCate subjectCate = subjectCateService.getOkById(answerSaveDTO.getSubjectCateId());
            if (Objects.isNull(subjectCate)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
            SubjectSecondDict subjectSecondDict = subjectSecondDictService.getOkById(subjectCate.getSubjectSecondDictId());
            if (Objects.isNull(subjectSecondDict)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
            answer.setSubjectDictId(subjectSecondDict.getSubjectDictId());
            answer.setSubjectFirstDictId(subjectSecondDict.getSubjectFirstDictId());
            answer.setSubjectSecondDictId(subjectSecondDict.getId());
            answer.setSubjectCateId(answerSaveDTO.getSubjectCateId());
        } else {
            answer.setActivityId(answerSaveDTO.getActivityId());
        }
        // 保存答题信息
        answer.setUserId(user.getId());
        answer.setStatus(1);
        ReflectUtil.setCreateInfo(answer, Answer.class);
        this.save(answer);
    }
}

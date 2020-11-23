package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SubjectCate;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.SubjectCateMapper;
import com.dong.mingjiuzhang.service.SubjectCateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学科题目分类表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class SubjectCateServiceImpl extends ServiceImpl<SubjectCateMapper, SubjectCate> implements SubjectCateService {

    /**
     * 根据二级类目获取分类列表
     *
     * @param subjectSecondDictId
     * @return
     */
    @Override
    public List<SubjectCate> getListBySubjectSecondDictId(Long subjectSecondDictId) {
        return this.list(new LambdaQueryWrapper<SubjectCate>().eq(SubjectCate::getSubjectSecondDictId, subjectSecondDictId)
                .eq(SubjectCate::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(SubjectCate::getSort).orderByAsc(SubjectCate::getId));
    }
}

package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SubjectSecondDict;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.SubjectSecondDictMapper;
import com.dong.mingjiuzhang.service.SubjectSecondDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学科二级类目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class SubjectSecondDictServiceImpl extends ServiceImpl<SubjectSecondDictMapper, SubjectSecondDict> implements SubjectSecondDictService {

    @Override
    public SubjectSecondDict getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<SubjectSecondDict>().eq(SubjectSecondDict::getId, id).eq(SubjectSecondDict::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 根据一级类目获取二级类目列表
     *
     * @param subjectFirstDictId
     * @return
     */
    @Override
    public List<SubjectSecondDict> getListBySubjectFirstDictId(Long subjectFirstDictId) {
        return this.list(new LambdaQueryWrapper<SubjectSecondDict>().eq(SubjectSecondDict::getSubjectFirstDictId, subjectFirstDictId)
                .eq(SubjectSecondDict::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(SubjectSecondDict::getSort).orderByAsc(SubjectSecondDict::getId));
    }
}

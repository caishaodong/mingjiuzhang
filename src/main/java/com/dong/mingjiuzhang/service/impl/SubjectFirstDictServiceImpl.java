package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SubjectFirstDict;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.SubjectFirstDictMapper;
import com.dong.mingjiuzhang.service.SubjectFirstDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学科一级类目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class SubjectFirstDictServiceImpl extends ServiceImpl<SubjectFirstDictMapper, SubjectFirstDict> implements SubjectFirstDictService {

    /**
     * 根据学科id获取学科以及类目列表
     *
     * @param subjectDictId
     * @return
     */
    @Override
    public List<SubjectFirstDict> getListBySubjectDictId(Long subjectDictId) {
        return this.list(new LambdaQueryWrapper<SubjectFirstDict>().eq(SubjectFirstDict::getSubjectDictId, subjectDictId)
                .eq(SubjectFirstDict::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(SubjectFirstDict::getSort).orderByAsc(SubjectFirstDict::getId));
    }
}

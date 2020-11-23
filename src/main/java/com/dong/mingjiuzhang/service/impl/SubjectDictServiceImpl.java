package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SubjectDict;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.SubjectDictMapper;
import com.dong.mingjiuzhang.service.SubjectDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学科表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class SubjectDictServiceImpl extends ServiceImpl<SubjectDictMapper, SubjectDict> implements SubjectDictService {

    /**
     * 获取所有学科列表（不分页）
     *
     * @return
     */
    @Override
    public List<SubjectDict> getList() {
        return this.list(new LambdaQueryWrapper<SubjectDict>().eq(SubjectDict::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(SubjectDict::getSort).orderByAsc(SubjectDict::getId));
    }
}

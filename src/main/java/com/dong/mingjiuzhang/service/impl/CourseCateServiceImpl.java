package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.CourseCate;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.CourseCateMapper;
import com.dong.mingjiuzhang.service.CourseCateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程类目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class CourseCateServiceImpl extends ServiceImpl<CourseCateMapper, CourseCate> implements CourseCateService {

    @Override
    public CourseCate getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<CourseCate>().eq(CourseCate::getId, id).eq(CourseCate::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取所有列表（不分页）
     *
     * @return
     */
    @Override
    public List<CourseCate> getList() {
        return this.list(new LambdaQueryWrapper<CourseCate>().eq(CourseCate::getIsDeleted, YesNoEnum.NO)
                .orderByAsc(CourseCate::getSort));
    }
}

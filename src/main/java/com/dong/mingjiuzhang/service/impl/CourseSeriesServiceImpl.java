package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.CourseSeriesMapper;
import com.dong.mingjiuzhang.service.CourseSeriesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程系列表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class CourseSeriesServiceImpl extends ServiceImpl<CourseSeriesMapper, CourseSeries> implements CourseSeriesService {

    @Override
    public CourseSeries getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<CourseSeries>().eq(CourseSeries::getId, id).eq(CourseSeries::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 根据课程类目id获取系列列表（不分页）
     *
     * @param courseCateId
     * @return
     */
    @Override
    public List<CourseSeries> getListByCourseCateId(Long courseCateId) {
        return this.list(new LambdaQueryWrapper<CourseSeries>().eq(CourseSeries::getCourseCateId, courseCateId)
                .eq(CourseSeries::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(CourseSeries::getSort).orderByAsc(CourseSeries::getId));
    }
}

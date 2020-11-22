package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;

import java.util.List;

/**
 * <p>
 * 课程系列表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseSeriesService extends IService<CourseSeries> {

    CourseSeries getOkById(Long id);

    /**
     * 根据课程类目id获取系列列表（不分页）
     *
     * @param courseCateId
     * @return
     */
    List<CourseSeries> getListByCourseCateId(Long courseCateId);
}

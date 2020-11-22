package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseService extends IService<Course> {

    Course getOKById(Long id);

    /**
     * 根据系列id获取所有课程列表
     *
     * @param courseSeriesId
     * @return
     */
    List<Course> getListByCourseSeriesId(Long courseSeriesId);

    /**
     * 根据课程id获取课程详细信息
     *
     * @param courseId
     * @return
     */
    CourseVo getCourseInfoByCourseId(Long courseId);

    /**
     * 获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    IPage<CourseWork> pageList(CourseWorkSearchDTO courseWorkSearchDTO);
}

package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.vo.CourseVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkCountVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkVO;

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
    CourseVO getCourseInfoByCourseId(Long courseId);


    /**
     * 根据老师id获取待批改作业数量
     *
     * @param teacherId
     * @return
     */
    List<CourseWorkCountVO> courseWorkCount(Long teacherId);

    /**
     * 根据课程id获取待修改作业列表
     *
     * @param teacherId
     * @param courseCateId
     * @param courseWorkId
     * @return
     */
    List<CourseWorkVO> courseWorkList(Long teacherId, Long courseCateId, Long courseWorkId);
}

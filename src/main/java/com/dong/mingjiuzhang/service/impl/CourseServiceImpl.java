package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.domain.entity.vo.CourseVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkCountVO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.mapper.CourseMapper;
import com.dong.mingjiuzhang.service.CourseSeriesService;
import com.dong.mingjiuzhang.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseSeriesService courseSeriesService;

    @Override
    public Course getOKById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Course>().eq(Course::getId, id).eq(Course::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 根据系列id获取所有课程列表
     *
     * @param courseSeriesId
     * @return
     */
    @Override
    public List<Course> getListByCourseSeriesId(Long courseSeriesId) {
        return this.list(new LambdaQueryWrapper<Course>().eq(Course::getCourseSeriesId, courseSeriesId)
                .eq(Course::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(Course::getSort).orderByAsc(Course::getId));
    }

    /**
     * 根据课程id获取课程详细信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseVO getCourseInfoByCourseId(Long courseId) {
        // 获取课程信息
        Course course = this.getOKById(courseId);
        if (Objects.isNull(course)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 获取课程系列信息
        CourseSeries courseSeries = courseSeriesService.getById(course.getCourseSeriesId());
        if (Objects.isNull(courseSeries)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        CourseVO courseVo = new CourseVO();
        copyCourse(courseVo, course);
        copyCourseSeries(courseVo, courseSeries);
        return courseVo;
    }

    /**
     * 根据老师id获取待批改作业数量
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<CourseWorkCountVO> courseWorkCount(Long teacherId) {
        return this.baseMapper.courseWorkCount(teacherId);
    }

    /**
     * 复制course信息
     *
     * @param courseVo
     * @param course
     */
    public void copyCourse(CourseVO courseVo, Course course) {
        courseVo.setId(course.getId());
        courseVo.setCourseCateId(course.getCourseCateId());
        courseVo.setCourseSeriesId(course.getCourseSeriesId());
        courseVo.setIsPayment(course.getIsPayment());
        courseVo.setCoursePrice(course.getCoursePrice());
        courseVo.setVideoUrl(course.getVideoUrl());
        courseVo.setCourseName(course.getCourseName());
        courseVo.setGmtStartShow(course.getGmtStartShow());
        courseVo.setGmtEndShow(course.getGmtEndShow());
        courseVo.setSynopsis(course.getSynopsis());
        courseVo.setSort(course.getSort());
        courseVo.setGmtCreate(course.getGmtCreate());
        courseVo.setGmtModified(course.getGmtModified());
    }

    /**
     * 复制courseSeries信息
     *
     * @param courseVo
     * @param courseSeries
     */
    public void copyCourseSeries(CourseVO courseVo, CourseSeries courseSeries) {
        courseVo.setCourseSeriesName(courseSeries.getCourseSeriesName());
        courseVo.setCourseSeriesGroupPrice(courseSeries.getCourseSeriesGroupPrice());
        courseVo.setGmtStartUpload(courseSeries.getGmtStartUpload());
        courseVo.setGmtEndUpload(courseSeries.getGmtEndUpload());
        courseVo.setGmtStartEffect(courseSeries.getGmtStartEffect());
        courseVo.setGmtEndEffect(courseSeries.getGmtEndEffect());
        courseVo.setCourseSpecial(courseSeries.getCourseSpecial());
        courseVo.setCourseEdition(courseSeries.getCourseEdition());
        courseVo.setFeedbackMode(courseSeries.getFeedbackMode());
        courseVo.setContent(courseSeries.getContent());
    }

}

package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.vo.CourseVo;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/course")
public class CourseController extends BaseController {
    @Autowired
    private CourseService courseService;

    /**
     * 根据系列id获取课程列表（不分页）
     *
     * @param courseSeriesId 课程系列id
     * @return
     */
    @GetMapping("list/{courseSeriesId}")
    public ResponseResult<List<Course>> list(@PathVariable("courseSeriesId") Long courseSeriesId) {
        List<Course> list = courseService.getListByCourseSeriesId(courseSeriesId);
        return success(list);
    }

    /**
     * 根据课程id获取课程详细信息
     *
     * @param courseId 课程id
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public ResponseResult<CourseVo> getCourseInfo(@PathVariable("courseId") Long courseId) {
        CourseVo courseVo = courseService.getCourseInfoByCourseId(courseId);
        return success(courseVo);
    }

}

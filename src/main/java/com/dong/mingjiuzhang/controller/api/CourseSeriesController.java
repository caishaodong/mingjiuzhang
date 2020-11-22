package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.CourseCate;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.CourseSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程系列
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/courseSeries")
public class CourseSeriesController extends BaseController {
    @Autowired
    private CourseSeriesService courseSeriesService;

    /**
     * 根据课程类目id获取课程系列列表（不分页）
     * @param courseCateId 课程类目id
     * @return
     */
    @GetMapping("list/{courseCateId}")
    public ResponseResult<List<CourseSeries>> list(@PathVariable("courseCateId") Long courseCateId) {
        List<CourseSeries> list = courseSeriesService.getListByCourseCateId(courseCateId);
        return success(list);
    }

}

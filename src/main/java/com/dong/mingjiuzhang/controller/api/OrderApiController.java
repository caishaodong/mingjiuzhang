package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.domain.entity.dto.OrderSaveDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.PayTypeEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.service.CourseSeriesService;
import com.dong.mingjiuzhang.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 订单
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@RestController
@RequestMapping("/order")
public class OrderApiController extends BaseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseSeriesService courseSeriesService;

    /**
     * 提交订单
     *
     * @param orderSaveDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody OrderSaveDTO orderSaveDTO) {
        // 参数校验
        orderSaveDTO.paramCheck();

        Long courseId = null;
        Course course = null;
        Long courseSeriesId = null;
        CourseSeries courseSeries = null;
        if (Objects.equals(orderSaveDTO.getPayType(), PayTypeEnum.COURSE.getType())) {
            // 购买的是课程
            courseId = orderSaveDTO.getCourseId();
            // 获取课程信息
            course = courseService.getOKById(courseId);
            if (Objects.isNull(course)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
            courseSeriesId = course.getCourseSeriesId();
            // 获取系列信息
            courseSeries = courseSeriesService.getOkById(courseSeriesId);
            if (Objects.isNull(courseSeries)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
        } else {
            courseSeriesId = orderSaveDTO.getCourseSeriesId();
            // 获取系列信息
            courseSeries = courseSeriesService.getOkById(courseSeriesId);
            if (Objects.isNull(courseSeries)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
        }
        return success();
    }

}

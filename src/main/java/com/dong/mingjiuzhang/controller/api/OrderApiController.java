package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.domain.entity.SysCity;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.OrderSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.PreOrderDTO;
import com.dong.mingjiuzhang.domain.entity.vo.OrderSaveVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.constant.OrderConstants;
import com.dong.mingjiuzhang.global.enums.*;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.pay.PayUtil;
import com.dong.mingjiuzhang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * (API)订单
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@RestController
@RequestMapping("/api/order")
public class OrderApiController extends BaseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseSeriesService courseSeriesService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysCityService sysCityService;
    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     *
     * @param request
     * @param orderSaveDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(HttpServletRequest request, @RequestBody OrderSaveDTO orderSaveDTO) {
        // 参数校验
        orderSaveDTO.paramCheck();
        User user = userService.getUserByToken(request);

        // 支付方式校验
        PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnum(orderSaveDTO.getPayMethod());
        if (Objects.isNull(payMethodEnum)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        Long courseId = null;
        Course course = null;
        Long courseSeriesId = null;
        CourseSeries courseSeries = null;
        SysCity sysCity = null;
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
            // 获取省
            sysCity = sysCityService.getSysCityByCode(orderSaveDTO.getProvinceCode());
            if (Objects.isNull(sysCity)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
        } else {
            courseSeriesId = orderSaveDTO.getCourseSeriesId();
            // 获取系列信息
            courseSeries = courseSeriesService.getOkById(courseSeriesId);
            if (Objects.isNull(courseSeries)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
            YesNoEnum yesNoEnum = YesNoEnum.getYesNoEnumByValue(orderSaveDTO.getIsGroup());
            if (Objects.isNull(yesNoEnum)) {
                throw new BusinessException(BusinessEnum.PARAM_ERROR);
            }
        }
        // 获取老师信息
        Long teacherId = courseSeries.getTeacherId();
        User teacher = userService.getOkById(teacherId);
        if (Objects.isNull(teacher)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 订单数据预处理
        PreOrderDTO preOrderDTO = new PreOrderDTO();
        setPreOrder(preOrderDTO, user, orderSaveDTO, course, courseSeries, sysCity, teacher);

        // 保存订单
        OrderSaveVO orderSaveVO = orderService.saveOrder(preOrderDTO);
        return success(orderSaveVO);
    }

    /**
     * 订单数据预处理
     *
     * @param preOrderDTO
     * @param user
     * @param orderSaveDTO
     * @param course
     * @param courseSeries
     * @param sysCity
     * @param teacher
     */
    public void setPreOrder(PreOrderDTO preOrderDTO, User user, OrderSaveDTO orderSaveDTO, Course course, CourseSeries courseSeries, SysCity sysCity, User teacher) {
        // 支付方式
        preOrderDTO.setPayMethod(orderSaveDTO.getPayMethod());
        // 用户信息
        preOrderDTO.setUserId(user.getId());
        preOrderDTO.setUserGrade(user.getGrade());
        preOrderDTO.setSendType(orderSaveDTO.getSendType());
        if (Objects.equals(orderSaveDTO.getSendType(), SendTypeEnum.SELF.getType())) {
            preOrderDTO.setReceiverName(orderSaveDTO.getReceiverName());
            preOrderDTO.setReceiverMobile(orderSaveDTO.getReceiverMobile());
            preOrderDTO.setProvinceCode(orderSaveDTO.getProvinceCode());
            preOrderDTO.setProvince(sysCity.getName());
            preOrderDTO.setAddress(orderSaveDTO.getAddress());
        }
        // 支付状态
        preOrderDTO.setPayStatus(PayStatusEnum.PROCESSING.getPayStatus());
        preOrderDTO.setStatus(OrderStatusEnum.PROCESSING.getStatus());
        // 购买类型
        if (Objects.equals(orderSaveDTO.getPayType(), PayTypeEnum.COURSE.getType())) {
            // 购买课程
            preOrderDTO.setShouldPrice(course.getCoursePrice());
            preOrderDTO.setPayType(PayTypeEnum.COURSE.getType());
            preOrderDTO.setCourseId(course.getId());
            preOrderDTO.setCourseName(course.getCourseName());
            preOrderDTO.setCourseSeriesId(courseSeries.getId());
            preOrderDTO.setCourseSeriesName(courseSeries.getCourseSeriesName());
            preOrderDTO.setTeacherId(teacher.getId());
            preOrderDTO.setTeacherName(teacher.getUsername());
            preOrderDTO.setTeacherMobile(teacher.getMobile());
            preOrderDTO.setIsGroup(YesNoEnum.NO.getValue());
        } else {
            // 购买系列
            if (Objects.equals(orderSaveDTO.getIsGroup(), YesNoEnum.NO.getValue())) {
                // 如果不是拼团
                preOrderDTO.setShouldPrice(courseSeries.getCourseSeriesPrice());
                preOrderDTO.setIsGroup(YesNoEnum.NO.getValue());
            } else {
                // 如果是拼团
                preOrderDTO.setShouldPrice(courseSeries.getCourseSeriesGroupPrice());
                preOrderDTO.setIsGroup(YesNoEnum.YES.getValue());
                preOrderDTO.setGroupCount(OrderConstants.GROUP_COUNT);
                preOrderDTO.setCurrentGroupCount(0);
                preOrderDTO.setGroupStatus(GroupStatusEnum.GROUPING.getStatus());
                preOrderDTO.setGmtGroupExpiration(LocalDateTime.now().plusSeconds(OrderConstants.GROUP_ORDER_EFFECTIVE_SECONDS));
            }
            preOrderDTO.setPayType(PayTypeEnum.COURSE_SERIES.getType());
            preOrderDTO.setCourseSeriesId(courseSeries.getId());
            preOrderDTO.setCourseSeriesName(courseSeries.getCourseSeriesName());
            preOrderDTO.setTeacherId(teacher.getId());
            preOrderDTO.setTeacherName(teacher.getUsername());
            preOrderDTO.setTeacherMobile(teacher.getMobile());
        }
        // 订单失效时间
        preOrderDTO.setGmtOrderExpiration(LocalDateTime.now().plusSeconds(OrderConstants.ORDER_EFFECTIVE_SECONDS));
    }

    /**
     * 支付回调
     */
    @PostMapping("/payBack")
    public void payBack() {
        PayUtil.payBack();
    }

}

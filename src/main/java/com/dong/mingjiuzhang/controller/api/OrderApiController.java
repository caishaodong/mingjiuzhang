package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.*;
import com.dong.mingjiuzhang.domain.entity.dto.OrderSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.PreOrderDTO;
import com.dong.mingjiuzhang.domain.entity.vo.OrderSaveVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.constant.OrderConstants;
import com.dong.mingjiuzhang.global.enums.*;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.pay.OrderPayBackDTO;
import com.dong.mingjiuzhang.global.util.pay.OrderPayService;
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
    @Autowired
    private OrderPayService orderPayService;

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
        OrderSaveVO orderSaveVO = orderPayService.genOrder(preOrderDTO);
        return success(orderSaveVO);
    }

    /**
     * 支付回调
     */
    @PostMapping("/payBack")
    public void payBack() {
        OrderPayBackDTO orderPayBackDTO = new OrderPayBackDTO();
        orderPayService.payBack(orderPayBackDTO);
    }

    /**************************************以上是支付相关接口，以下是公共方法**************************************/

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
        // 提取方式
        preOrderDTO.setSendType(orderSaveDTO.getSendType());
        // 支付方式
        preOrderDTO.setPayMethod(orderSaveDTO.getPayMethod());

        // 支付状态
        preOrderDTO.setPayStatus(PayStatusEnum.PROCESSING.getPayStatus());
        preOrderDTO.setStatus(OrderStatusEnum.PROCESSING.getStatus());
        // 购买类型
        if (Objects.equals(orderSaveDTO.getPayType(), PayTypeEnum.COURSE.getType())) {
            // 用户信息
            setUserInfo(preOrderDTO, user, orderSaveDTO, sysCity);
            // 购买课程
            preOrderDTO.setShouldPrice(course.getCoursePrice());
            preOrderDTO.setCourseId(course.getId());
            preOrderDTO.setCourseName(course.getCourseName());
            // 设置系列信息
            setCourseSeriesInfo(preOrderDTO, courseSeries, teacher, PayTypeEnum.COURSE);
            preOrderDTO.setIsGroup(YesNoEnum.NO.getValue());
        } else {
            // 购买系列
            if (Objects.equals(orderSaveDTO.getIsGroup(), YesNoEnum.NO.getValue())) {
                // 如果不是拼团
                // 用户信息
                setUserInfo(preOrderDTO, user, orderSaveDTO, sysCity);
                // 设置系列信息
                setCourseSeriesInfo(preOrderDTO, courseSeries, teacher, PayTypeEnum.COURSE_SERIES);

                preOrderDTO.setShouldPrice(courseSeries.getCourseSeriesPrice());
                preOrderDTO.setIsGroup(YesNoEnum.NO.getValue());
            } else {
                // 如果是拼团
                // 获取当前该系列待拼团订单
                Order groupOrder = orderService.getGroupOrder(courseSeries.getId(), user.getId());
                if (Objects.isNull(groupOrder)) {
                    // 第一次拼团
                    // 用户信息
                    setUserInfo(preOrderDTO, user, orderSaveDTO, sysCity);
                    // 设置系列信息
                    setCourseSeriesInfo(preOrderDTO, courseSeries, teacher, PayTypeEnum.COURSE_SERIES);

                    preOrderDTO.setShouldPrice(courseSeries.getCourseSeriesGroupPrice());
                    preOrderDTO.setIsGroup(YesNoEnum.YES.getValue());
                    preOrderDTO.setGroupCount(OrderConstants.GROUP_COUNT);
                    // 拼团人数默认为1
                    preOrderDTO.setCurrentGroupCount(1);
                    preOrderDTO.setGroupStatus(GroupStatusEnum.GROUPING.getStatus());
                    preOrderDTO.setGmtGroupExpiration(LocalDateTime.now().plusSeconds(OrderConstants.GROUP_ORDER_EFFECTIVE_SECONDS));
                } else {
                    // 参与别人的拼团
                    // 用户信息
                    setUserInfo(preOrderDTO, user, orderSaveDTO, sysCity);
                    preOrderDTO.setShouldPrice(courseSeries.getCourseSeriesGroupPrice());

                    preOrderDTO.setGroupOrder(groupOrder);
                    preOrderDTO.setSlaveUser(user);
                }

            }
        }
        // 订单失效时间
        preOrderDTO.setGmtOrderExpiration(LocalDateTime.now().plusSeconds(OrderConstants.ORDER_EFFECTIVE_SECONDS));

    }

    /**
     * 设置系列信息
     *
     * @param courseSeries
     * @param teacher
     * @paramo preOrderDTO
     */
    public void setCourseSeriesInfo(PreOrderDTO preOrderDTO, CourseSeries courseSeries, User teacher, PayTypeEnum payTypeEnum) {
        preOrderDTO.setPayType(payTypeEnum.getType());
        preOrderDTO.setCourseSeriesId(courseSeries.getId());
        preOrderDTO.setCourseSeriesName(courseSeries.getCourseSeriesName());
        preOrderDTO.setTeacherId(teacher.getId());
        preOrderDTO.setTeacherName(teacher.getUsername());
        preOrderDTO.setTeacherMobile(teacher.getMobile());
    }

    /**
     * 设置用户信息
     *
     * @param preOrderDTO
     * @param user
     * @param orderSaveDTO
     * @param sysCity
     */
    public void setUserInfo(PreOrderDTO preOrderDTO, User user, OrderSaveDTO orderSaveDTO, SysCity sysCity) {
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
    }


}

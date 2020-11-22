package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSaveDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.service.CourseService;
import com.dong.mingjiuzhang.service.CourseWorkService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * (API)作业
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/courseWork")
public class CourseWorkController extends BaseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseWorkService courseWorkService;

    /**
     * 上传作业
     *
     * @param request
     * @param courseWorkSaveDTO
     * @return
     */
    @PostMapping("save")
    public ResponseResult save(HttpServletRequest request, @RequestBody CourseWorkSaveDTO courseWorkSaveDTO) {
        // 参数校验
        courseWorkSaveDTO.paramCheck();
        // 获取用户信息
        User user = userService.getUserByToken(request);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }

        Course course = courseService.getOKById(courseWorkSaveDTO.getCourseId());
        if (Objects.isNull(course)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 保存信息
        CourseWork courseWork = new CourseWork();
        courseWork.setUserId(user.getId());
        courseWork.setCourseCateId(course.getCourseCateId());
        courseWork.setCourseSeriesId(course.getCourseSeriesId());
        courseWork.setCourseId(course.getId());
        courseWork.setWorkImage(courseWorkSaveDTO.getWorkImage());
        ReflectUtil.setCreateInfo(courseWork, CourseWork.class);
        courseWorkService.save(courseWork);
        return success();
    }

}

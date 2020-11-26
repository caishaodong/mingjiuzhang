package com.dong.mingjiuzhang.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseSeries;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.CorrectWorkDTO;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkCountVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkPageVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.UserIdentityEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.CourseSeriesService;
import com.dong.mingjiuzhang.service.CourseService;
import com.dong.mingjiuzhang.service.CourseWorkService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private CourseSeriesService courseSeriesService;
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
    @PostMapping("/save")
    public ResponseResult save(HttpServletRequest request, @RequestBody CourseWorkSaveDTO courseWorkSaveDTO) {
        // 参数校验
        courseWorkSaveDTO.paramCheck();
        // 获取用户信息
        User user = userService.getUserByToken(request);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }

        // 获取课程信息
        Course course = courseService.getOKById(courseWorkSaveDTO.getCourseId());
        if (Objects.isNull(course)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 获取系列信息
        CourseSeries courseSeries = courseSeriesService.getOkById(course.getCourseSeriesId());
        if (Objects.isNull(courseSeries)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 保存信息
        CourseWork courseWork = new CourseWork();
        courseWork.setUserId(user.getId());
        courseWork.setCourseCateId(course.getCourseCateId());
        courseWork.setCourseSeriesId(course.getCourseSeriesId());
        courseWork.setCourseId(course.getId());
        courseWork.setWorkImage(courseWorkSaveDTO.getWorkImage());
        courseWork.setTeacherId(courseSeries.getTeacherId());
        courseWork.setIsFinishCorrect(YesNoEnum.NO.getValue());
        ReflectUtil.setCreateInfo(courseWork, CourseWork.class);
        courseWorkService.save(courseWork);
        return success();
    }

    /**
     * 获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    @PostMapping("/pageList")
    public ResponseResult<PageUtil<CourseWorkPageVO>> pageList(@RequestBody CourseWorkSearchDTO courseWorkSearchDTO) {
        // 参数校验
//        courseWorkSearchDTO.paramCheck();
        IPage<CourseWorkPageVO> page = courseWorkService.pageList(courseWorkSearchDTO);
        return success(page);
    }

    /**
     * 获取待批改作业数量
     *
     * @param request
     * @return
     */
    @GetMapping("/courseWorkCount")
    public ResponseResult<List<CourseWorkCountVO>> courseWorkCount(HttpServletRequest request) {
        User user = userService.getUserByToken(request);
        if (!StringUtil.equals(user.getIdentity(), UserIdentityEnum.TEACHER.getIdentity())) {
            throw new BusinessException(BusinessEnum.NOT_TEACHER);
        }
        List<CourseWorkCountVO> list = courseService.courseWorkCount(user.getId());
        return success(list);
    }

    /**
     * 根据课程id获取待修改作业列表
     *
     * @param request
     * @param courseCateId 课程类目id
     * @return
     */
    @GetMapping("/courseWorkList/{courseCateId}")
    public ResponseResult<List<CourseWorkVO>> courseWorkList(HttpServletRequest request, @PathVariable("courseCateId") Long courseCateId) {
        User user = userService.getUserByToken(request);
        if (!StringUtil.equals(user.getIdentity(), UserIdentityEnum.TEACHER.getIdentity())) {
            throw new BusinessException(BusinessEnum.NOT_TEACHER);
        }
        List<CourseWorkVO> list = courseService.courseWorkList(user.getId(), courseCateId, null);
        return success(list);
    }

    /**
     * 获取作业详情
     *
     * @param request
     * @param courseWorkId 作业id
     * @return
     */
    @GetMapping("/courseWork/{courseWorkId}")
    public ResponseResult<CourseWorkVO> courseWork(HttpServletRequest request, @PathVariable("courseWorkId") Long courseWorkId) {
        User user = userService.getUserByToken(request);
        if (!StringUtil.equals(user.getIdentity(), UserIdentityEnum.TEACHER.getIdentity())) {
            throw new BusinessException(BusinessEnum.NOT_TEACHER);
        }
        List<CourseWorkVO> list = courseService.courseWorkList(user.getId(), null, courseWorkId);
        CourseWorkVO courseWorkVO = CollectionUtils.isEmpty(list) ? null : list.get(0);
        return success(courseWorkVO);
    }

    /**
     * 批改作业
     *
     * @param request
     * @param correctWorkDTO
     * @return
     */
    @PutMapping("/correctWork")
    public ResponseResult correctWork(HttpServletRequest request, @RequestBody CorrectWorkDTO correctWorkDTO) {
        User user = userService.getUserByToken(request);
        if (!StringUtil.equals(user.getIdentity(), UserIdentityEnum.TEACHER.getIdentity())) {
            throw new BusinessException(BusinessEnum.NOT_TEACHER);
        }

        CourseWork courseWork = courseWorkService.getOkById(correctWorkDTO.getCourseWorkId());
        if (Objects.isNull(courseWork)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        courseWork.setWorkImage(correctWorkDTO.getWorkImage());
        courseWorkService.updateById(courseWork);
        return success();
    }

    /**
     * 完成批改作业
     *
     * @param request
     * @param courseWorkId 作业id
     * @return
     */
    @PutMapping("/finishCorrect/{courseWorkId}")
    public ResponseResult finishCorrect(HttpServletRequest request, @PathVariable("courseWorkId") Long courseWorkId) {
        User user = userService.getUserByToken(request);
        if (!StringUtil.equals(user.getIdentity(), UserIdentityEnum.TEACHER.getIdentity())) {
            throw new BusinessException(BusinessEnum.NOT_TEACHER);
        }

        CourseWork courseWork = courseWorkService.getOkById(courseWorkId);
        if (Objects.isNull(courseWork)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        courseWork.setIsFinishCorrect(YesNoEnum.YES.getValue());
        courseWorkService.updateById(courseWork);
        return success();
    }


}

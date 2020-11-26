package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkPageVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkVO;

import java.util.List;

/**
 * <p>
 * 作业表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseWorkService extends IService<CourseWork> {
    CourseWork getOkById(Long id);

    /**
     * 学生获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    IPage<CourseWorkPageVO> pageList(CourseWorkSearchDTO courseWorkSearchDTO);

    /**
     * 老师根据课程id获取待修改作业列表
     *
     * @param teacherId
     * @param courseCateId
     * @param courseWorkId
     * @return
     */
    List<CourseWorkVO> courseWorkList(Long teacherId, Long courseCateId, Long courseWorkId);

    /**
     * 学生获取作业详情
     *
     * @param userId
     * @param courseWorkId
     * @return
     */
    CourseWorkVO getCourseWorkInfo(Long userId, Long courseWorkId);
}

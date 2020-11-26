package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkPageVO;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 作业表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseWorkMapper extends BaseMapper<CourseWork> {

    /**
     * 学生获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    IPage<CourseWorkPageVO> pageList(@Param("courseWorkSearchDTO") CourseWorkSearchDTO courseWorkSearchDTO);

    /**
     * 老师根据课程id获取待修改作业列表
     *
     * @param teacherId
     * @param courseCateId
     * @param courseWorkId
     * @return
     */
    List<CourseWorkVO> courseWorkList(@Param("teacherId") Long teacherId, @Param("courseCateId") Long courseCateId, @Param("courseWorkId") Long courseWorkId);

    /**
     * 学生获取作业详情
     *
     * @param userId
     * @param courseWorkId
     * @return
     */
    CourseWorkVO getCourseWorkInfo(@Param("userId") Long userId, @Param("courseWorkId") Long courseWorkId);
}

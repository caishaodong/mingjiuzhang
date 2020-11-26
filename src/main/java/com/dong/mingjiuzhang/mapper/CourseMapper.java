package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.vo.CourseWorkCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据老师id获取待批改作业数量
     *
     * @param teacherId
     * @return
     */
    List<CourseWorkCountVO> courseWorkCount(@Param("teacherId") Long teacherId);

}

package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.Course;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;

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
     * 获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    IPage<CourseWork> pageList(CourseWorkSearchDTO courseWorkSearchDTO);
}

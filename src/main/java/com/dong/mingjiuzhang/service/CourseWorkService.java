package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.dong.mingjiuzhang.domain.entity.dto.CourseWorkSearchDTO;

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
     * 获取上传作业列表（分页）
     *
     * @param courseWorkSearchDTO
     * @return
     */
    IPage<CourseWork> pageList(CourseWorkSearchDTO courseWorkSearchDTO);
}

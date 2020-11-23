package com.dong.mingjiuzhang.service;

import com.dong.mingjiuzhang.domain.entity.CourseWork;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

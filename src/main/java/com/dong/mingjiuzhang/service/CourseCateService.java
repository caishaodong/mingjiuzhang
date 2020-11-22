package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.CourseCate;

import java.util.List;

/**
 * <p>
 * 课程类目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface CourseCateService extends IService<CourseCate> {

    CourseCate getOkById(Long id);

    /**
     * 获取所有列表（不分页）
     *
     * @return
     */
    List<CourseCate> getList();
}

package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SubjectDict;

import java.util.List;

/**
 * <p>
 * 学科表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectDictService extends IService<SubjectDict> {

    /**
     * 获取所有学科列表（不分页）
     *
     * @return
     */
    List<SubjectDict> getList();
}

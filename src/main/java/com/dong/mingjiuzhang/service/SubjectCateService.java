package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SubjectCate;

import java.util.List;

/**
 * <p>
 * 学科题目分类表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectCateService extends IService<SubjectCate> {

    SubjectCate getOkById(Long id);

    /**
     * 根据二级类目获取分类列表
     *
     * @param subjectSecondDictId
     * @return
     */
    List<SubjectCate> getListBySubjectSecondDictId(Long subjectSecondDictId);
}

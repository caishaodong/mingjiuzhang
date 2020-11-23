package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SubjectSecondDict;

import java.util.List;

/**
 * <p>
 * 学科二级类目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectSecondDictService extends IService<SubjectSecondDict> {

    /**
     * 根据一级类目获取二级类目列表
     *
     * @param subjectFirstDictId
     * @return
     */
    List<SubjectSecondDict> getListBySubjectFirstDictId(Long subjectFirstDictId);
}

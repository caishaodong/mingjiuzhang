package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SubjectFirstDict;

import java.util.List;

/**
 * <p>
 * 学科一级类目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectFirstDictService extends IService<SubjectFirstDict> {

    /**
     * 根据学科id获取学科以及类目列表
     *
     * @param subjectDictId
     * @return
     */
    List<SubjectFirstDict> getListBySubjectDictId(Long subjectDictId);
}

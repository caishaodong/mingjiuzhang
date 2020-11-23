package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SubjectRequest;
import com.dong.mingjiuzhang.domain.entity.vo.SubjectRequestVO;

import java.util.List;

/**
 * <p>
 * 学科题目表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectRequestService extends IService<SubjectRequest> {

    /**
     * 根据分类id获取题目列表
     *
     * @param subjectCateId
     * @return
     */
    List<SubjectRequestVO> getListBySubjectCateId(Long subjectCateId);
}

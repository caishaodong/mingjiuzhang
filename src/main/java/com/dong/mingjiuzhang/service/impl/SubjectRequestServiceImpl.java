package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SubjectRequest;
import com.dong.mingjiuzhang.domain.entity.vo.SubjectRequestVO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.SubjectRequestMapper;
import com.dong.mingjiuzhang.service.SubjectRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学科题目表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class SubjectRequestServiceImpl extends ServiceImpl<SubjectRequestMapper, SubjectRequest> implements SubjectRequestService {

    /**
     * 根据分类id获取题目列表
     *
     * @param subjectCateId
     * @return
     */
    @Override
    public List<SubjectRequestVO> getListBySubjectCateId(Long subjectCateId) {
        return this.baseMapper.getListBySubjectCateId(subjectCateId);
    }
}

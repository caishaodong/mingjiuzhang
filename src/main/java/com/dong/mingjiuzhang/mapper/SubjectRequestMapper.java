package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.SubjectRequest;
import com.dong.mingjiuzhang.domain.entity.vo.SubjectRequestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学科题目表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface SubjectRequestMapper extends BaseMapper<SubjectRequest> {

    /**
     * 根据分类id获取题目列表
     *
     * @param subjectCateId
     * @return
     */
    List<SubjectRequestVO> getListBySubjectCateId(@Param("subjectCateId") Long subjectCateId);
}

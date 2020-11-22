package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.Problem;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 问题表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface ProblemMapper extends BaseMapper<Problem> {

    /**
     * 获取问题列表（分页）
     *
     * @param problemSearchDTO
     * @return
     */
    IPage<ProblemVO> pageList(@Param("problemSearchDTO") ProblemSearchDTO problemSearchDTO);
}

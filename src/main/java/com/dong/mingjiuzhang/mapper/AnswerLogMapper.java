package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.AnswerLog;
import com.dong.mingjiuzhang.domain.entity.dto.WrongCollectionDTO;
import com.dong.mingjiuzhang.domain.entity.vo.WrongCollectionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 答题记录表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface AnswerLogMapper extends BaseMapper<AnswerLog> {

    /**
     * 错题集
     *
     * @param wrongCollectionDTO
     * @return
     */
    IPage<List<WrongCollectionVO>> wrongCollectionList(@Param("wrongCollectionDTO") WrongCollectionDTO wrongCollectionDTO);
}

package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Problem;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemVO;

/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface ProblemService extends IService<Problem> {

    Problem getOkById(Long id);

    /**
     * 提交问题
     *
     * @param problemSaveDTO
     * @param userId
     */
    void save(ProblemSaveDTO problemSaveDTO, Long userId);

    /**
     * 获取问题列表（分页）
     *
     * @param problemSearchDTO
     * @return
     */
    IPage<ProblemVO> pageList(ProblemSearchDTO problemSearchDTO);

    /**
     * 根据问题id获取问题及回复信息
     *
     * @param problemId
     * @return
     */
    ProblemVO getProblemInfo(Long problemId);
}

package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Problem;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemReplyVO;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemVO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.ProblemMapper;
import com.dong.mingjiuzhang.service.ProblemReplyService;
import com.dong.mingjiuzhang.service.ProblemService;
import com.dong.mingjiuzhang.service.UserService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemReplyService problemReplyService;

    @Override
    public Problem getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Problem>().eq(Problem::getId, id).eq(Problem::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 提交问题
     *
     * @param problemSaveDTO
     * @param userId
     */
    @Override
    public void save(ProblemSaveDTO problemSaveDTO, Long userId) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemSaveDTO, problem);
        problem.setUserId(userId);
        problem.setProblemStatus(YesNoEnum.YES.getValue());
        problem.setReplyCount(0);
        ReflectUtil.setCreateInfo(problem, Problem.class);
        this.save(problem);
    }

    /**
     * 获取问题列表（分页）
     *
     * @param problemSearchDTO
     * @return
     */
    @Override
    public IPage<ProblemVO> pageList(ProblemSearchDTO problemSearchDTO) {
        IPage<ProblemVO> page = this.baseMapper.pageList(problemSearchDTO);
        return page;
    }

    /**
     * 根据问题id获取问题及回复信息
     *
     * @param problemId
     * @return
     */
    @Override
    public ProblemVO getProblemInfo(Long problemId) {
        // 获取问题信息
        Problem problem = this.getOkById(problemId);
        if (Objects.isNull(problem)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        // 获取用户信息
        User user = userService.getOkById(problem.getUserId());
        user = Objects.isNull(user) ? new User() : user;

        // 根据问题id获取回复列表
        List<ProblemReplyVO> problemReplyVOList = problemReplyService.getListByProblemId(problemId);

        // 封装返回内容
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        problemVO.setUsername(user.getUsername());
        problemVO.setAvatar(user.getAvatar());
        problemVO.setProblemReplyVOList(problemReplyVOList);

        return problemVO;
    }
}

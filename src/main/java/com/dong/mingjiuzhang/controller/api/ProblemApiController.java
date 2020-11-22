package com.dong.mingjiuzhang.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSaveDTO;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemSearchDTO;
import com.dong.mingjiuzhang.domain.entity.vo.ProblemVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.service.ProblemService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 问题
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/problem")
public class ProblemApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemService problemService;

    /**
     * 提交问题
     *
     * @param request
     * @param problemSaveDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(HttpServletRequest request, @RequestBody ProblemSaveDTO problemSaveDTO) {
        // 参数校验
        problemSaveDTO.paramCheck();
        User user = userService.getUserByToken(request);
        problemService.save(problemSaveDTO, user.getId());
        return success();
    }

    /**
     * 获取问题列表（分页）
     *
     * @param problemSearchDTO
     * @return
     */
    @GetMapping("/pageList")
    public ResponseResult<PageUtil<ProblemVO>> pageList(@RequestBody ProblemSearchDTO problemSearchDTO) {
        IPage<ProblemVO> page = problemService.pageList(problemSearchDTO);
        return success(page);
    }

    /**
     * 根据问题id获取问题及回复信息
     *
     * @param problemId
     * @return
     */
    @GetMapping("/getProblemInfo/{problemId}")
    public ResponseResult<ProblemVO> getProblemInfo(@PathVariable("problemId") Long problemId) {
        ProblemVO problemVO = problemService.getProblemInfo(problemId);
        return success(problemVO);
    }


}

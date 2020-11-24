package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Problem;
import com.dong.mingjiuzhang.domain.entity.ProblemReply;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.ProblemReplyDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.service.ProblemReplyService;
import com.dong.mingjiuzhang.service.ProblemService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * (API)问题回复
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/problemReply")
public class ProblemReplyApiController extends BaseController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemReplyService problemReplyService;

    /**
     * 添加回复
     *
     * @param request
     * @param problemReplyDTO
     * @return
     */
    @PostMapping("save")
    public ResponseResult save(HttpServletRequest request, @RequestBody ProblemReplyDTO problemReplyDTO) {
        // 参数校验
        problemReplyDTO.paramCheck();
        User user = userService.getUserByToken(request);

        // 校验问题是否存在
        Problem problem = problemService.getOkById(problemReplyDTO.getProblemId());
        if (Objects.isNull(problem)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 保存回复
        ProblemReply problemReply = new ProblemReply();
        BeanUtils.copyProperties(problemReplyDTO, problemReply);
        problemReply.setUserId(user.getId());
        problemReply.setProblemReplyStatus(YesNoEnum.YES.getValue());
        ReflectUtil.setCreateInfo(problemReply, ProblemReply.class);
        problemReplyService.save(problemReply);

        return success();
    }

}

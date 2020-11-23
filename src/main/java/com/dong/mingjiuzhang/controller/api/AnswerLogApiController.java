package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Answer;
import com.dong.mingjiuzhang.domain.entity.SubjectRequest;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerLogSaveDTO;
import com.dong.mingjiuzhang.domain.entity.vo.WrongCollectionVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.service.AnswerLogService;
import com.dong.mingjiuzhang.service.AnswerService;
import com.dong.mingjiuzhang.service.SubjectRequestService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * (API)答题记录
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/answerLog")
public class AnswerLogApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerLogService answerLogService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private SubjectRequestService subjectRequestService;

    /**
     * 保存答题记录
     *
     * @param answerLogSaveDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AnswerLogSaveDTO answerLogSaveDTO) {
        // 参数校验
        answerLogSaveDTO.paramCheck();
        // 校验答题是否存在
        Answer answer = answerService.getOkById(answerLogSaveDTO.getAnswerId());
        if (Objects.isNull(answer)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 校验题目是否存在
        SubjectRequest subjectRequest = subjectRequestService.getOkById(answerLogSaveDTO.getSubjectRequestId());
        if (Objects.isNull(subjectRequest)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 保存答题记录
        answerLogService.save(answer.getUserId(), answerLogSaveDTO);
        return success();
    }

    /**
     * 错题集
     *
     * @param request
     * @return
     */
    @GetMapping("/wrongCollectionList")
    public ResponseResult<List<WrongCollectionVO>> wrongCollectionList(HttpServletRequest request) {
        User user = userService.getUserByToken(request);
        List<WrongCollectionVO> list = answerLogService.wrongCollectionList(user.getId());
        return success(list);
    }
}

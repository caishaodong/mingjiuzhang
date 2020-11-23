package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.AnswerSaveDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.AnswerService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * (API)答题
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    /**
     * 开始答题
     *
     * @param request
     * @param answerSaveDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(HttpServletRequest request, @RequestBody AnswerSaveDTO answerSaveDTO) {
        // 参数校验
        answerSaveDTO.paramCheck();
        User user = userService.getUserByToken(request);
        // 保存答题
        answerService.save(user, answerSaveDTO);
        return success();
    }

}

package com.dong.mingjiuzhang.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.UserMessage;
import com.dong.mingjiuzhang.domain.entity.dto.UserMassageSearchDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.service.UserMessageService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * (API)消息
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/userMassage")
public class UserMessageApiController extends BaseController {
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserService userService;

    /**
     * 获取消息列表（分页）
     *
     * @param request
     * @param userMassageSearchDTO
     * @return
     */
    @GetMapping("/pageList")
    public ResponseResult<PageUtil<UserMessage>> pageList(HttpServletRequest request, @RequestBody UserMassageSearchDTO userMassageSearchDTO) {
        User user = userService.getUserByToken(request);
        IPage<UserMessage> page = userMessageService.pageList(user, userMassageSearchDTO);
        return success(page);
    }
}

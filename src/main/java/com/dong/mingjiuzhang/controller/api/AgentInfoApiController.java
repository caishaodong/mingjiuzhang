package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.AgentInfo;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.service.AgentInfoService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-22 13:27
 * @Description 代理信息
 **/
@RestController
@RequestMapping("admin/agentInfo")
public class AgentInfoApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AgentInfoService agentInfoService;

    /**
     * 提交代理信息
     *
     * @param request
     * @param agentInfo
     * @return
     */
    @PostMapping("save")
    public ResponseResult save(HttpServletRequest request, @RequestBody AgentInfo agentInfo) {
        // 参数校验
        agentInfo.paramCheck();
        User user = userService.getUserByToken(request);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }

        // 保存代理信息
        agentInfo.setUserId(user.getId());
        agentInfoService.save(agentInfo);
        ReflectUtil.setCreateInfo(agentInfo, AgentInfo.class);
        return success();
    }

}

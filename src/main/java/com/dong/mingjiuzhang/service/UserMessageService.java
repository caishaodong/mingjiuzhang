package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.UserMessage;
import com.dong.mingjiuzhang.domain.entity.dto.UserMassageSearchDTO;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
public interface UserMessageService extends IService<UserMessage> {

    /**
     * 获取消息列表（分页）
     *
     * @param user
     * @param userMassageSearchDTO
     * @return
     */
    IPage<UserMessage> pageList(User user, UserMassageSearchDTO userMassageSearchDTO);
}

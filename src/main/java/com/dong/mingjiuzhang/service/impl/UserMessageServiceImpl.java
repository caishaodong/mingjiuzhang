package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.UserMessage;
import com.dong.mingjiuzhang.domain.entity.dto.UserMassageSearchDTO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.UserMessageMapper;
import com.dong.mingjiuzhang.service.UserMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    /**
     * 获取消息列表（分页）
     *
     * @param user
     * @param userMassageSearchDTO
     * @return
     */
    @Override
    public IPage<UserMessage> pageList(User user, UserMassageSearchDTO userMassageSearchDTO) {
        IPage<UserMessage> page = this.page(userMassageSearchDTO, new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByDesc(UserMessage::getGmtCreate));
        return page;
    }
}

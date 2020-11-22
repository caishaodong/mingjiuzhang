package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-11-22 20:15
 * @Description
 **/
@Data
public class ProblemReplyVO {
    /**
     * 回复id
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 图像url
     */
    private String avatar;

    /**
     * 回复内容
     */
    private String problemReplyContent;

    /**
     * 回复内容图片（json）
     */
    private String problemReplyImage;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}

package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.util.page.PageUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-26 17:23
 * @Description
 **/
@Data
public class WrongCollectionDTO extends PageUtil {
    /**
     * 用户id
     */
    private Long userId;
}

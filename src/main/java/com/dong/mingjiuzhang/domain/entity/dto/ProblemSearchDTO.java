package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.util.page.PageUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-22 19:56
 * @Description
 **/
@Data
public class ProblemSearchDTO extends PageUtil {
    /**
     * 列表查询类型：new最新，hot最热
     */
    private String type;
}

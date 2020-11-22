package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-22 10:43
 * @Description
 **/
@Data
public class SysCityVO {
    /**
     * 省编码
     */
    private Integer provinceCode;

    /**
     * 市编码
     */
    private Integer cityCode;

    /**
     * 区编码
     */
    private Integer areaCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;
}

package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.CourseCate;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.CourseCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)课程类目
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@RestController
@RequestMapping("api/courseCate")
public class CourseCateController extends BaseController {
    @Autowired
    private CourseCateService courseCateService;

    /**
     * 获取所有课程类目列表（不分页）
     *
     * @return
     */
    @GetMapping("list")
    public ResponseResult<List<CourseCate>> list() {
        List<CourseCate> list = courseCateService.getList();
        return success(list);
    }

}

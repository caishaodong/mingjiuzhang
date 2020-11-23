package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.SubjectDict;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SubjectDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)学科
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/subjectDict")
public class SubjectDictApiController extends BaseController {
    @Autowired
    private SubjectDictService subjectDictService;

    /**
     * 获取所有学科列表（不分页）
     *
     * @return
     */
    public ResponseResult<List<SubjectDict>> list() {
        List<SubjectDict> list = subjectDictService.getList();
        return success(list);
    }

}

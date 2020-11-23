package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.SubjectSecondDict;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SubjectSecondDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)学科二级类目
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/subjectSecondDict")
public class SubjectSecondDictApiController extends BaseController {
    @Autowired
    private SubjectSecondDictService subjectSecondDictService;

    /**
     * 根据一级类目获取二级类目列表
     *
     * @param subjectFirstDictId 一级类目id
     * @return
     */
    @GetMapping("list/{subjectFirstDictId}")
    public ResponseResult<List<SubjectSecondDict>> list(@PathVariable("subjectFirstDictId") Long subjectFirstDictId) {
        List<SubjectSecondDict> list = subjectSecondDictService.getListBySubjectFirstDictId(subjectFirstDictId);
        return success(list);
    }
}

package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.SubjectCate;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SubjectCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)学科题目分类
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/subjectCate")
public class SubjectCateApiController extends BaseController {

    @Autowired
    private SubjectCateService subjectCateService;

    /**
     * 根据二级类目获取分类列表
     *
     * @param subjectSecondDictId
     * @return
     */
    @GetMapping("list/{subjectSecondDictId}")
    public ResponseResult<List<SubjectCate>> list(@PathVariable("subjectSecondDictId") Long subjectSecondDictId) {
        List<SubjectCate> list = subjectCateService.getListBySubjectSecondDictId(subjectSecondDictId);
        return success(list);
    }

}

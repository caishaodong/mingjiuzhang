package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.SubjectFirstDict;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SubjectFirstDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)学科一级类目
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/subjectFirstDict")
public class SubjectFirstDictApiController extends BaseController {
    @Autowired
    private SubjectFirstDictService subjectFirstDictService;

    /**
     * 根据学科id获取一级类目列表
     *
     * @param subjectDictId 学科id
     * @return
     */
    @GetMapping("list/{subjectDictId}")
    public ResponseResult<List<SubjectFirstDict>> list(@PathVariable("subjectDictId") Long subjectDictId) {
        List<SubjectFirstDict> list = subjectFirstDictService.getListBySubjectDictId(subjectDictId);
        return success(list);
    }

}

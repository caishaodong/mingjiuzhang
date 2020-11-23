package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.vo.SubjectRequestVO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SubjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (API)学科题目
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/subjectRequest")
public class SubjectRequestApiController extends BaseController {
    @Autowired
    private SubjectRequestService subjectRequestService;

    /**
     * 根据分类id获取题目列表
     *
     * @param subjectCateId
     * @return
     */
    @GetMapping("list/{subjectCateId}")
    public ResponseResult<List<SubjectRequestVO>> list(@PathVariable("subjectCateId") Long subjectCateId) {
        List<SubjectRequestVO> list = subjectRequestService.getListBySubjectCateId(subjectCateId);
        return success(list);
    }

}

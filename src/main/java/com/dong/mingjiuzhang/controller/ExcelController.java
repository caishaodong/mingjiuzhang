package com.dong.mingjiuzhang.controller;

import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.util.date.LocalDateTimeUtil;
import com.dong.mingjiuzhang.global.util.file.download.zip.ZipDownLoadUtil;
import com.dong.mingjiuzhang.global.util.file.excel.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author caishaodong
 * @Date 2020-10-16 10:50
 * @Description
 **/
@RestController
@RequestMapping("/templates.excel")
public class ExcelController extends BaseController {

    @GetMapping("/exportByTemplate")
    public ResponseResult exportByTemplate(HttpServletResponse response) {
        String templatePath = System.getProperty("user.dir") + "/src/main/resources/templates/excel/exportTemplate.xlsx";
        Map<String, String> map = getExpList();
        try {
            ExcelUtil.createSheetByTemplate(templatePath, map, response);
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return success();
    }

    public Map<String, String> getExpList() {
        Map<String, String> map = new HashMap<>();
        map.put("date", LocalDateTimeUtil.formatDateTimeByPattern(LocalDateTime.now(), "yyyy-MM-dd"));
        map.put("sales", "1234");
        map.put("customservice", "客服张三");
        map.put("customername", "金主爸爸");
        map.put("customertype", "客户类型1");
        // 需求简述
        map.put("demandBrief", "balabala");
        // 合同条款
        map.put("cbmTerms", "巴拉巴拉巴拉");
        //客户要求
        map.put("customerDemand", "这是一个很无理的要求");
        // 财经法务意见
        map.put("financialLegalOpinion", "财经法务表示没意见");
        // 法务意见
        map.put("legalOpinion", "法务表示很无语");
        // 升级处理说明
        map.put("upgradeDealInstruction", "随便升级");
        // 升级处理人（自动显示为特批人）
        map.put("upgradeDealPerson", "李四说我不背锅");
        return map;
    }

    @GetMapping("/download")
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response, String[] urls) {
        urls = new String[]{
                "https://xnjz.oss-cn-hangzhou.aliyuncs.com/icon-res/ec20f447-7041-45bf-a52d-38a6ebc0e689.jpg",
                "https://xnjz.oss-cn-hangzhou.aliyuncs.com/icon-res/f3c7151b-ba32-4c76-9d14-2a85b0f49124.jpg",
                "https://xnjz.oss-cn-hangzhou.aliyuncs.com/shop-res/091815363517123456.pdf",
                "https://xnjz.oss-cn-hangzhou.aliyuncs.com/shop-res/091815363517_0中华美文.pdf"
        };

        try {
            ZipDownLoadUtil.download(urls, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

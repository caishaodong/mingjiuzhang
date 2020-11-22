package com.dong.mingjiuzhang.controller.admin;

import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description (管理台)文件上传
 **/
@RestController
@RequestMapping("admin/file")
public class FileAdminController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileAdminController.class);

    @Value("${file.upload.path}")
    private String uploadPath;
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return
     */
    @RequestMapping("/upload")
    public ResponseResult<String> upload(@RequestParam("file") MultipartFile file) {
        String filePath = fileService.upload(file, uploadPath);
        LOGGER.info("admin upload file, filePath=" + filePath);
        return success(filePath);
    }
}

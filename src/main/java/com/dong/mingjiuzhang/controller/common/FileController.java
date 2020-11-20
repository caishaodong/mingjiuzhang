package com.dong.mingjiuzhang.controller.common;

import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author caishaodong
 * @Date 2020-11-20 23:12
 * @Description
 **/
@Controller
@RequestMapping("api/file")
public class FileController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return
     */
    @RequestMapping("/upload")
    public ResponseResult<String> upload(@RequestParam("file") MultipartFile file) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            File targetFile = new File(uploadPath + file.getOriginalFilename());
            out = new FileOutputStream(targetFile);
            FileCopyUtils.copy(in, out);
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
            return error();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String filePath = "";
        return success(filePath);
    }
}

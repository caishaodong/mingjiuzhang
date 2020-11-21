package com.dong.mingjiuzhang.service.impl;

import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author caishaodong
 * @Date 2020-11-21 15:18
 * @Description
 **/
@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file, String uploadPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            File targetFile = new File(uploadPath + file.getOriginalFilename());
            out = new FileOutputStream(targetFile);
            FileCopyUtils.copy(in, out);
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
            return "";
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
        return filePath;
    }
}

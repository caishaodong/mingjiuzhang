package com.dong.mingjiuzhang.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author caishaodong
 * @Date 2020-11-21 15:17
 * @Description
 **/
public interface FileService {
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String upload(MultipartFile file, String uploadPath);
}

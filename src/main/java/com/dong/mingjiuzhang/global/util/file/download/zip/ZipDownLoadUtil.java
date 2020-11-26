package com.dong.mingjiuzhang.global.util.file.download.zip;

import com.dong.mingjiuzhang.global.util.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author caishaodong
 * @Date 2020-10-19 15:43
 * @Description zip工具类
 **/
public class ZipDownLoadUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(ZipDownLoadUtil.class);

    /**
     * 多个网络文件下载为zip文件
     *
     * @param urls
     * @param request
     * @param response
     * @throws Exception
     */
    public static void download(String[] urls, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置压缩包的名字
        String zipName = System.currentTimeMillis() + ".zip";
        zipName = "这是我自定义的名称.zip";

        ZipOutputStream zos = null;
        DataOutputStream os = null;
        try {
            // 响应头的设置
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(zipName, "UTF-8"));

            // 设置压缩流：直接写入response，实现边压缩边下载
            zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            // 设置压缩方法
            zos.setMethod(ZipOutputStream.DEFLATED);

            // 循环将文件写入压缩流

            for (int i = 0; i < urls.length; i++) {
                String fileName = FileUtil.getNameNoGarbled(request, FileUtil.getFileNameByUrl(urls[i]));
                LOGGER.info("fileName={}", fileName);

                File file = FileUtil.getFileByUrl(urls[i], fileName);
                // 添加ZipEntry，并在ZipEntry中写入文件流
                zos.putNextEntry(new ZipEntry(fileName));
                os = new DataOutputStream(zos);
                InputStream is = new FileInputStream(file);
                byte[] b = new byte[1024];
                int length = 0;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                is.close();
                zos.closeEntry();
            }
        } finally {
            os.flush();
            if (os != null) {
                os.close();
            }
            if (zos != null) {
                zos.close();
            }
        }
    }
}

package com.dong.mingjiuzhang.global.util.file;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Author caishaodong
 * @Date 2020-10-19 15:48
 * @Description 文件工具类
 **/
public class FileUtil {
    /**
     * url转file
     *
     * @param fileUrl
     * @param suffix
     * @return
     */
    public static File getFileByUrl(String fileUrl, String suffix) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        BufferedOutputStream stream = null;
        InputStream inputStream = null;
        File file;
        try {
            URL imageUrl = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            file = File.createTempFile("file", suffix);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(outStream.toByteArray());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
        return file;
    }

    /**
     * 根据url获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileNameByUrl(String url) {
        return StringUtil.isBlank(url) ? "" : url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 解决不同浏览器压缩包名字含有中文时乱码的问题
     *
     * @param fileName
     * @return
     */
    public static String getNameNoGarbled(HttpServletRequest request, String fileName) throws Exception {
        String agent = request.getHeader("USER-AGENT");
        // 解决不同浏览器压缩包名字含有中文时乱码的问题
        if (agent.contains("MSIE") || agent.contains("Trident")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        return fileName;
    }
}

package com.dong.mingjiuzhang.global.config.docs;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @Author caishaodong
 * @Date 2020-09-02 12:05
 * @Description
 **/
public class JApiDocsConfig {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("user.home"));
        generateDocs();
    }

    /**
     * 生成 JApiDocs
     * 文档访问地址：http://127.0.0.1:9090/docs/V1.0/index.html
     */
    public static void generateDocs() {
        DocsConfig config = new DocsConfig();
        String projectPath = System.getProperty("user.dir");
        String docPath = projectPath + "\\src\\main\\resources\\static\\docs";
        // 项目根目录
        config.setProjectPath(projectPath);
        // 项目名称
        config.setProjectName("MingJiuZhang");
        // 声明该API的版本
        config.setApiVersion("V1.0");
        // 生成API 文档所在目录
        config.setDocsPath(docPath);
        // 配置自动生成
        config.setAutoGenerate(Boolean.TRUE);
        Docs.buildHtmlDocs(config);
    }
}

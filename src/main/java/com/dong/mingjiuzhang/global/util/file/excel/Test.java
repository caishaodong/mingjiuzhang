package com.dong.mingjiuzhang.global.util.file.excel;

import java.io.File;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:22
 * @Description
 **/
public class Test {
    public static void main(String[] args) {
        new Test().test();
    }

    public void test() {
        String file = "C:\\Users\\15158\\Downloads\\2020-09-10订单信息.xls";
        File file1 = new File(file);
//        try {
//            //传入一个文件，调用readExcel()读取文件，返回List<ExcelSheetPO>
//            List<ExcelSheetPO> list = ExcelUtil.readExcel(file1, null, null);
//            for (ExcelSheetPO a : list) {
//                System.out.println(a.getHeaders() + ".." + a.getTitle() + ".." + a.getSheetName());
//                for (List<Object> b : a.getDataList()) {
//                    System.out.println(b);
//                }
//            }
//            //调用createWorkbookAtDisk()生成excel
//            ExcelUtil.createWorkbookAtDisk(ExcelVersion.V2007, list, "D:\\resres.xlsx");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}

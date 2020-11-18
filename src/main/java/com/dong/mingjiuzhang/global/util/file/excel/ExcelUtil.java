package com.dong.mingjiuzhang.global.util.file.excel;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dong.mingjiuzhang.global.util.decimal.DecimalFormatUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:19
 * @Description Excel工具类
 **/
public class ExcelUtil {
    /**
     * 标题样式
     */
    private final static String STYLE_HEADER = "header";
    /**
     * 表头样式
     */
    private final static String STYLE_TITLE = "title";
    /**
     * 数据样式
     */
    private final static String STYLE_DATA = "data";

    /**
     * 存储样式
     */
    private static HashMap<String, CellStyle> cellStyleMap = new HashMap<>();

    /**
     * 读取Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<ExcelSheetPO> readExcel(MultipartFile file) throws IOException {
        return readExcel(file, null, null);
    }

    /**
     * 读取excel文件里面的内容 支持日期，数字，字符，函数公式，布尔类型
     *
     * @param file
     * @param rowCount
     * @param columnCount
     */
    public static List<ExcelSheetPO> readExcel(MultipartFile file, Integer rowCount, Integer columnCount)
            throws IOException {

        // 根据后缀名称判断excel的版本
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Workbook wb = null;
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            wb = new HSSFWorkbook(file.getInputStream());

        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            wb = new XSSFWorkbook(file.getInputStream());

        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<>();
        // 解析sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<Object>> dataList = new ArrayList<>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                readRowCount = sheet.getPhysicalNumberOfRows();
            } else {
                readRowCount = rowCount;
            }
            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;
                if (columnCount == null || columnCount > row.getLastCellNum()) {
                    readColumnCount = (int) row.getLastCellNum();
                } else {
                    readColumnCount = columnCount;
                }
                List<Object> rowValue = new LinkedList<Object>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);
                    rowValue.add(getCellValue(wb, cell));
                }
                dataList.add(rowValue);
            }
            sheetPOs.add(sheetPO);
        }
        return sheetPOs;
    }

    private static Object getCellValue(Workbook wb, Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            // 格式化 number
            DecimalFormat df = new DecimalFormat("0");
            // String
            // 字符
            // 格式化日期字符串
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 格式化数字
            DecimalFormat nf = new DecimalFormat("0.00");
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;
                default:
                    columnValue = cell.toString();
            }
        }
        return columnValue;
    }

    /**
     * 在硬盘上写入excel文件
     *
     * @param version
     * @param excelSheets
     * @param filePath
     */
    public static void createWorkbookAtDisk(ExcelVersion version, List<ExcelSheetPO> excelSheets, String filePath)
            throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        createWorkbookAtOutStream(version, excelSheets, fileOut, true);
    }

    /**
     * 把excel表格写入输出流中，输出流会被关闭
     *
     * @param version
     * @param excelSheets
     * @param outStream
     * @param closeStream 是否关闭输出流
     */
    public static void createWorkbookAtOutStream(ExcelVersion version, List<ExcelSheetPO> excelSheets,
                                                 OutputStream outStream, boolean closeStream) throws IOException {
        if (CollectionUtils.isEmpty(excelSheets)) {
            return;
        }
        Workbook wb = createWorkBook(version, excelSheets);
        wb.write(outStream);
        if (closeStream) {
            outStream.close();
        }
    }

    /**
     * 导出到浏览器
     *
     * @param excelSheets
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void exportToBrowser(List<ExcelSheetPO> excelSheets, String fileName, HttpServletResponse response) throws IOException {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        Workbook wb = null;


        ExcelVersion excelVersion = getExcelVersionByFileName(fileName);
        if (Objects.isNull(excelVersion)) {
            throw new RuntimeException("文件格式错误");
        }
        switch (excelVersion) {
            case V2003:
                response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
                wb = createWorkBook(ExcelVersion.V2003, excelSheets);
                break;
            case V2007:
                response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                wb = createWorkBook(ExcelVersion.V2007, excelSheets);
                break;
            default:
                throw new RuntimeException("文件格式错误");
        }

        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private static Workbook createWorkBook(ExcelVersion version, List<ExcelSheetPO> excelSheets) {
        Workbook wb = createWorkbook(version);
        for (int i = 0; i < excelSheets.size(); i++) {
            ExcelSheetPO excelSheetPO = excelSheets.get(i);
            if (excelSheetPO.getSheetName() == null) {
                excelSheetPO.setSheetName("sheet" + i);
            }
            // 过滤特殊字符
            Sheet tempSheet = wb.createSheet(WorkbookUtil.createSafeSheetName(excelSheetPO.getSheetName()));
            buildSheetData(wb, tempSheet, excelSheetPO, version);
        }
        return wb;
    }

    private static void buildSheetData(Workbook wb, Sheet sheet, ExcelSheetPO excelSheetPO, ExcelVersion version) {
        // 校验是否需要序号列，如果需要，则进行转换
        setSortColumn(excelSheetPO);
        // 设置行宽
        setColumnWith(sheet, excelSheetPO);
        // 有需要可以自定义title和header
        createTitle(sheet, excelSheetPO, wb, version);
        createHeader(sheet, excelSheetPO, wb, version);
        createBody(sheet, excelSheetPO, wb, version);
        // 添加尾行
        addTail(sheet, excelSheetPO, wb, version);
    }

    /**
     * 内容
     *
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     */
    private static void createBody(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        List<List<Object>> dataList = excelSheetPO.getDataList();
        for (int i = 0; i < dataList.size() && i < version.getMaxRow(); i++) {
            List<Object> values = dataList.get(i);
            Row row = sheet.createRow(2 + i);
//            Row row = sheet.createRow(i);
            for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
                CellStyle style = getStyle(STYLE_DATA, wb);
                cell.setCellStyle(style);
                cell.setCellValue(Objects.nonNull(values.get(j)) ? values.get(j).toString() : "");
//                if (values.get(j) instanceof Number) {
//                    cell.setCellValue(new Double(values.get(j).toString()));
//                } else {
//                    cell.setCellValue(values.get(j).toString());
//
//                }
            }
        }

    }

    /**
     * 生成header
     *
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     */
    private static void createHeader(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        String[] headers = excelSheetPO.getHeaders();

        if (headers == null) {
            Cell cellHeader = null;
        } else {
            Row row = sheet.createRow(1);
            for (int i = 0; i < headers.length && i < version.getMaxColumn(); i++) {
                Cell cellHeader = row.createCell(i);
                cellHeader.setCellStyle(getStyle(STYLE_HEADER, wb));
                cellHeader.setCellValue(headers[i]);
            }
        }
    }

    /**
     * 生成title
     *
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     */
    private static void createTitle(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        if (StringUtil.isBlank(excelSheetPO.getTitle())) {
            return;
        }
        Row titleRow = sheet.createRow(0);
        Cell titleCel = titleRow.createCell(0);
        excelSheetPO.setTitle(excelSheetPO.getTitle());
        titleCel.setCellValue(excelSheetPO.getTitle());
        titleCel.setCellStyle(getStyle(STYLE_TITLE, wb));
        // 限制最大列数
        int column = excelSheetPO.getHeaders().length > version.getMaxColumn() ? version.getMaxColumn() : excelSheetPO.getHeaders().length;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column - 1));
    }

    /**
     * 设置行宽
     *
     * @param sheet
     * @param excelSheetPO
     */
    private static void setColumnWith(Sheet sheet, ExcelSheetPO excelSheetPO) {
        sheet.setDefaultRowHeight((short) 400);
        sheet.setDefaultColumnWidth((short) 14);
        Map<Integer, Integer> columnWithMap = excelSheetPO.getColumnWidthMap();
        if (CollectionUtils.isEmpty(columnWithMap)) {
            return;
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = columnWithMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            Integer columnIndex = entry.getKey();
            Integer width = entry.getValue();
            sheet.setColumnWidth(columnIndex, width * 256);
        }


    }

    /**
     * 获取样式style
     *
     * @param type
     * @param wb
     * @return
     */
    private static CellStyle getStyle(String type, Workbook wb) {

        if (cellStyleMap.containsKey(type)) {
            return cellStyleMap.get(type);
        }

        // 生成一个样式
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setWrapText(true);
        style.setLocked(false);
//        style.setDataFormat(wb.createDataFormat().getFormat("@"));

        if (STYLE_TITLE.equals(type)) {
            style.setAlignment(HorizontalAlignment.CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 18);
            font.setBold(true);
            font.setFontName("宋体");
            style.setFont(font);
        } else if (STYLE_HEADER.equals(type)) {
            style.setAlignment(HorizontalAlignment.CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 14);
            font.setBold(true);
            font.setFontName("宋体");
            style.setFont(font);
        } else if (STYLE_DATA.equals(type)) {
            style.setAlignment(HorizontalAlignment.LEFT);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("宋体");
            style.setFont(font);
        }

        cellStyleMap.put(type, style);
        return style;
    }

    private static Workbook createWorkbook(ExcelVersion version) {
        switch (version) {
            case V2003:
                return new HSSFWorkbook();
            case V2007:
                return new XSSFWorkbook();
        }
        return null;
    }

    /**
     * 增加序号列
     *
     * @param excelSheetPO
     */
    private static void setSortColumn(ExcelSheetPO excelSheetPO) {
        Boolean needSort = excelSheetPO.getNeedSort();
        if (!needSort) {
            return;
        }
        // headers需要增加“序号”列
        String[] headers = excelSheetPO.getHeaders();
        if (headers.length > 0) {
            String[] newHeaders = new String[headers.length + 1];
            newHeaders[0] = "序号";
            for (int i = 0; i < headers.length; i++) {
                newHeaders[i + 1] = headers[i];
            }
            excelSheetPO.setHeaders(newHeaders);
        }

        // dataList需要给每一行数据其实增加递增序号
        List<List<Object>> dataList = excelSheetPO.getDataList();
        if (!CollectionUtils.isEmpty(dataList)) {
            List<List<Object>> newDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                List<Object> objList = dataList.get(i);
                List<Object> newObjList = new ArrayList<>(objList.size() + 1);
                newObjList.add(0, i + 1);
                for (int m = 0; m < objList.size(); m++) {
                    newObjList.add(m + 1, objList.get(m));
                }
                newDataList.add(i, newObjList);
            }
            excelSheetPO.setDataList(newDataList);
        }

        // columnWidthMap需要给行宽列递增一个列序号
        Map<Integer, Integer> columnWidthMap = excelSheetPO.getColumnWidthMap();
        if (!CollectionUtils.isEmpty(columnWidthMap)) {
            Map<Integer, Integer> newColumnWidthMap = new HashMap<>(columnWidthMap.size());
            for (Map.Entry<Integer, Integer> entry : columnWidthMap.entrySet()) {
                newColumnWidthMap.put(entry.getKey() + 1, entry.getValue());
            }
            excelSheetPO.setColumnWidthMap(newColumnWidthMap);
        }
    }

    /**
     * 根据文件名称返回Excel版本
     *
     * @param fileName
     * @return
     */
    public static ExcelVersion getExcelVersionByFileName(String fileName) {
        if (fileName.endsWith(".xls")) {
            return ExcelVersion.V2003;
        } else if (fileName.endsWith(".xlsx")) {
            return ExcelVersion.V2007;
        } else {
            return null;
        }
    }

    /**
     * 添加尾行
     *
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     */
    private static void addTail(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        List<Map<Integer, Object>> tailList = excelSheetPO.getTailMapList();
        if (CollectionUtils.isEmpty(tailList)) {
            return;
        }

        int columnCount = excelSheetPO.getHeaders().length;
        int dataRowCount = excelSheetPO.getDataList().size();

        for (int i = 0; i < tailList.size(); i++) {
            // 创建行
            Row row = sheet.createRow(2 + i + dataRowCount);
            for (int k = 0; k < columnCount && k < version.getMaxColumn(); k++) {
                // 创建格子
                Cell cell = row.createCell(k);
                cell.setCellStyle(getStyle(STYLE_DATA, wb));
                // 获取需要填充值的列
                Map<Integer, Object> columnIndexValueMap = tailList.get(i);
                Set<Integer> columnIndexSet = columnIndexValueMap.keySet();
                if (columnIndexSet.contains(k)) {
                    Object value = columnIndexValueMap.get(k);
                    if (value instanceof Number) {
                        cell.setCellValue(DecimalFormatUtil.format("#.00", new BigDecimal(String.valueOf(value))));
                    } else {
                        cell.setCellValue(value.toString());
                    }
                } else {
                    cell.setCellValue("");
                }
            }
        }

//        // 长度转成ABC列
//        String colString = CellReference.convertNumToColString(column);
//        // 求和公式
//        String sumResult = "SUM(" + colString + "3:" + colString + (2 + size) + ")";
//        row.getCell(column).setCellFormula(sumResult);
//        row.getCell(column).setCellFormula(row.getCell(column).getCellFormula());
    }

    /**
     * 读取Excel模板，填充数据，导出Excel
     *
     * @param templateFilePath
     * @param map
     * @param response
     * @throws Exception
     */
    public static void createSheetByTemplate(String templateFilePath, Map<String, String> map, HttpServletResponse response) throws Exception {
        createSheetByTemplate(templateFilePath, 0, map, response);
    }

    /**
     * 读取Excel模板，填充数据，导出Excel
     *
     * @param templateFilePath
     * @param sheetNum         第一个sheet
     * @param map
     * @param response
     * @throws Exception
     */
    public static void createSheetByTemplate(String templateFilePath, int sheetNum, Map<String, String> map, HttpServletResponse response) throws Exception {
        String fileName = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
        Workbook wb;
        ExcelVersion excelVersion = getExcelVersionByFileName(fileName);
        if (Objects.isNull(excelVersion)) {
            throw new RuntimeException("文件格式错误");
        }
        switch (excelVersion) {
            case V2003:
                response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFilePath));
                wb = new HSSFWorkbook(fs);
                break;
            case V2007:
                response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                wb = new XSSFWorkbook(new FileInputStream(templateFilePath));
                break;
            default:
                throw new RuntimeException("文件格式错误");
        }

        // 替换模板数据
        replaceTemplateData(wb, sheetNum, map);

        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 替换模板数据
     *
     * @param wb
     * @param sheetNum
     * @param map
     */
    public static void replaceTemplateData(Workbook wb, int sheetNum, Map<String, String> map) {
        // 获取第一个sheet里的内容
        Sheet sheet = wb.getSheetAt(sheetNum);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        // 该sheet页里最多有几行内容
        int rowNum = sheet.getLastRowNum();
        // 循环每一行
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            // 该行存在几列
            int colNum = row.getLastCellNum();
            // 循环每一列
            for (int j = 0; j < colNum; j++) {
                Cell cell = row.getCell((short) j);
                // 获取单元格内容  （行列定位）
                String str = cell.getStringCellValue();
                // 替换excel中对应的键的值（注意，excel模板中的要替换的值必须跟map中的key值对应，不然替换不成功）
                if (map.keySet().contains(str)) {
                    //写入单元格内容
                    cell.setCellStyle(getStyle(STYLE_DATA, wb));
                    // 替换单元格内容
                    cell.setCellValue(map.get(str));
                }
            }
        }
    }
}

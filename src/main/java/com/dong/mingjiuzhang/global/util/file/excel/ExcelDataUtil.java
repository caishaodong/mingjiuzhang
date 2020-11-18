package com.dong.mingjiuzhang.global.util.file.excel;

import com.dong.mingjiuzhang.global.util.decimal.DecimalFormatUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:59
 * @Description
 **/
public class ExcelDataUtil {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 导出
     *
     * @param excelDataDTO
     * @param response
     * @param <T>
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void export(ExcelDataDTO<T> excelDataDTO, HttpServletResponse response)
            throws NoSuchFieldException, IllegalAccessException, IOException {

        // 表格数据
        List<List<Object>> dataList = new ArrayList<>();
        // 列名称
        String[] headers = new String[excelDataDTO.getFieldNameMap().size()];
        // 属性名
        LinkedList<String> fieldNameList = new LinkedList();
        // 列宽
        Map<Integer, Integer> columnWidth = new HashMap<>(16);

        // 把列名称放在第一行
        Iterator<Map.Entry<String, String[]>> mapIterator = excelDataDTO.getFieldNameMap().entrySet().iterator();
        int i = 0;
        while (mapIterator.hasNext()) {
            Map.Entry<String, String[]> next = mapIterator.next();
            String[] fieldNameAndColumnWidth = next.getValue();
            if (fieldNameAndColumnWidth.length > 1) {
                int width = Integer.parseInt(fieldNameAndColumnWidth[1]);
                if (width > 0) {
                    columnWidth.put(i, width);
                }
            }
            fieldNameList.add(fieldNameAndColumnWidth[0]);
            headers[i] = next.getKey();
            i++;
        }

        // 填充属性值
        for (Object data : excelDataDTO.getDataList()) {
            // 获取每一行的属性值
            List<Object> rowData = new ArrayList<>();
            Iterator<String> iterator = fieldNameList.iterator();
            while (iterator.hasNext()) {
                String fieldName = iterator.next();
                // 获取属性
                Field field = data.getClass().getDeclaredField(fieldName);
                // 获取属性值
                field.setAccessible(true);
                Object value = field.get(data);
                if (value instanceof LocalDateTime) {
                    value = DateTimeFormatter.ofPattern(PATTERN).format((LocalDateTime) value);
                } else if (value instanceof Date) {
                    value = new SimpleDateFormat(PATTERN).format(value);
                } else if (value instanceof BigDecimal) {
                    value = new BigDecimal(DecimalFormatUtil.format("#.00", new BigDecimal(String.valueOf(value))));
                }

                rowData.add(value);
            }
            dataList.add(rowData);
        }

        // 参数准备
        List<ExcelSheetPO> list = new ArrayList<>();
        ExcelSheetPO excelSheetPO = new ExcelSheetPO();
        excelSheetPO.setTitle(StringUtil.isBlank(excelDataDTO.getTitle()) ? "" : excelDataDTO.getTitle());
        excelSheetPO.setDataList(dataList);
        excelSheetPO.setHeaders(headers);
        excelSheetPO.setColumnWidthMap(Objects.isNull(columnWidth) ? new HashMap<>(16) : columnWidth);
        excelSheetPO.setNeedSort(excelDataDTO.getNeedSort());
        excelSheetPO.setTailMapList(excelDataDTO.getTailMapList());
        list.add(excelSheetPO);

        // 导出
        ExcelUtil.exportToBrowser(list, excelDataDTO.getFileName() + System.currentTimeMillis(), response);

    }
}

package com.test.base.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import xiao.li.com.base.exception.BaseException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Descriptions <br>
 *
 * @author taotao <br>
 * @version 1.0 <br>
 * @createDate 2020/06/03 9:34 上午 <br>
 * @see com.changhong.mcc.base.util <br>
 */
public class MccExcelUtil {

    /**
     *  2003- 版本的excel
     */
    private final static String EXCEL_2003L = ".xls";

    /**
     *  2007+ 版本的excel
     */
    private final static String EXCEL_2007U = ".xlsx";

    /**
     *  格子日期格式类型General
     */
    private final static String CELL_DATAFORMATSTRING_TYPE_GENERAL = "General";

    /**
     *  格子日期格式类型m/d/yy
     */
    private final static String CELL_DATAFORMATSTRING_TYPE_MDYY = "m/d/yy";

    /**
     * Description 自动实现导出excel时统一调用<br>
     *
     * @param fileName <br>
     * @param dataList <br>
     * @param titleMap <br>
     * @param response <br>
     * @throws Exception <br>
     * @author taotao <br>
     * @createDate 2020/6/3 10:29 上午 <br>
     **/
    public static <T> void autoExportExcel(String fileName, List<T> dataList, Map<String,String> titleMap, HttpServletResponse response)throws Exception{
        ExcelWriter writer = ExcelUtil.getWriter(true);
        for(Map.Entry<String, String> entry : titleMap.entrySet()){
            writer.addHeaderAlias(entry.getKey(),entry.getValue());
        }
        writer.autoSizeColumnAll();
        writer.setOnlyAlias(true);
        writer.write(dataList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    public static List<List<Object>> importExcel(MultipartFile file)  {
        String filename = file.getOriginalFilename();
        if(!MccExcelUtil.checkFileNameValid(filename)) {
            throw new BaseException("文件格式错误，必须是excel文件（扩展名为：.xlsx 或 .xls）");
        }

        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            throw new BaseException("解析文件流错误");
        }

        List<List<Object>> listob = null;
        try {
            listob = MccExcelUtil.getBankListByExcel(in, filename);
        } catch (Exception e) {
            throw new BaseException("解析Excel格式出错");
        }

        return listob;
    }

    public static boolean checkFileNameValid(String fileName) {
        if (fileName.endsWith(MccExcelUtil.EXCEL_2003L) || fileName.endsWith(MccExcelUtil.EXCEL_2007U)) {
            return true;
        }
        return false;
    }

    public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        return getBankListByExcel(in,  fileName,  false);
    }

    public static List<List<Object>> getBankListByExcel(InputStream in, String fileName, boolean includeFirstRow) throws Exception {
        // 创建Excel工作薄
        Workbook work = MccExcelUtil.getWorkbook(in, fileName);
        return getBankListByExcel(work, includeFirstRow);
    }

    public static List<List<Object>> getBankListByExcel(Workbook work, boolean includeFirstRow) throws Exception {
        List<List<Object>> list = null;
        // 创建Excel工作薄
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            // 遍历当前sheet中的所有行
            // 参考https://blog.csdn.net/yangzi_520/article/details/103311175，解决读取数据总是少一行的问题,getLastRowNum改为getPhysicalNumberOfRows
            for (int j = sheet.getFirstRowNum(); j <= sheet.getPhysicalNumberOfRows(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if(!includeFirstRow){
                    if (row.getFirstCellNum() == j) {
                        continue;
                    }
                }

                // 遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if (cell == null) {
                        li.add("");
                    } else {
                        li.add(MccExcelUtil.getCellValue(cell));
                    }
                }
                list.add(li);
            }
        }
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            // 2003-
            return new HSSFWorkbook(inStr);
        } else if (EXCEL_2007U.equals(fileType)) {
            // 2007+
            return new XSSFWorkbook(inStr);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        // 格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0.000");

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (CELL_DATAFORMATSTRING_TYPE_GENERAL.equals(cell.getCellStyle().getDataFormatString())) {
//				value = df.format(cell.getNumericCellValue());
                    value = String.valueOf(cell.getNumericCellValue());
                } else if (CELL_DATAFORMATSTRING_TYPE_MDYY.equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            case FORMULA:
                try {
                    value = df2.format(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            default:
                break;
        }
        return value;
    }
}

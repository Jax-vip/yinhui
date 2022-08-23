package com.yinhui.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author yinhui
 * @date 2022/8/23
 */
@Slf4j
public class CSVUtils {

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param map        csv文件的列表头map
     * @param outPutPath 生成文件路径
     * @param fileName   文件名称
     * @return
     */
    public static File createCSVFile(List exportData, LinkedHashMap map, String outPutPath, String fileName) {

        File file = new File(outPutPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        //定义文件名格式并创建
        File csvFile = new File(outPutPath + fileName + ".csv");

        try (FileOutputStream fileOutputStream = new FileOutputStream(csvFile)) {
            //加入bom 否则生成的csv文件excel乱码
            fileOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            try (BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(fileOutputStream
                    , "utf-8"), 1024)) {
                // 写入文件头部
                for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                    csvFileOutputStream.write((String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "");
                    if (propertyIterator.hasNext()) {
                        //如果需要生成正常的csv文件，则用","
                        csvFileOutputStream.write("@@");
                    }
                }
                csvFileOutputStream.newLine();
                // 写入文件内容
                for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                    Object row = (Object) iterator.next();
                    for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                        java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
                                .next();
                        //如果是空值则进行处理,用 "" 填充 否则会抛空指针
                        String va = "";
                        String property = BeanUtils.getProperty(row, (String) propertyEntry.getKey());
                        if (property != null && !property.equals("")) {
                            csvFileOutputStream.write(property);
                        } else {
                            csvFileOutputStream.write(va);
                        }
                        if (propertyIterator.hasNext()) {
                            //如果需要生成正常的csv文件，则用","
                            csvFileOutputStream.write("@@");
                        }
                    }
                    if (iterator.hasNext()) {
                        csvFileOutputStream.newLine();
                    }
                }
                csvFileOutputStream.flush();
            }
        } catch (Exception e) {
            log.error("生成CSV文件:{}失败", outPutPath + fileName, e);
        }
        return csvFile;
    }


    /**
     *
     * 测试数据
     *
     * @param args
     */
    public static void main(String[] args) {
        String name = "银行退款数据";
        List exportData = new ArrayList();

        LinkedHashMap datamMap = new LinkedHashMap();
        datamMap.put("1", "123456");
        datamMap.put("2", LocalDateTime.now());
        datamMap.put("3", 18);
        datamMap.put("4", "七天无理由");
        exportData.add(datamMap);
        exportData.add(datamMap);
        exportData.add(datamMap);

        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "订单号");
        map.put("2", "支付日期");
        map.put("3", "退货现金金额（整数金额 单位：分）");
        map.put("4", "退货原因");
        String filePath = "e:/";
        File file = CSVUtils.createCSVFile(exportData, map, filePath, name);//生成CSV文件

    }

}

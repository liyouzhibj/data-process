package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.domain.KeyWord;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service("keyWriteToExcel")
public class KeyWriteToExcel implements DataWrite<String, List<KeyWord>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String fileName, List<KeyWord> keyList)
    {
       // File file = new File(fileName);
        try
        {
            Workbook swb = new HSSFWorkbook();//WorkbookFactory.create(new FileInputStream(new File(fileName)));
          //  swb.write(new FileOutputStream(new File(fileName)));
            Sheet sheet = swb.createSheet();
            String[] head = {"序号", "关键字", "关键字次数", "翻译"};

            Row row = sheet.createRow(0);
            int length = head.length;
            for(int i =0;i<length;i++)
            {
                Cell cell = row.createCell(i);
                cell.setCellValue(head[i]);
            }
            int j = 0;
            for(KeyWord key:keyList)
            {
                row = sheet.createRow(j+1);

                Cell cell = row.createCell(0);
                cell.setCellValue(key.getId());

                cell = row.createCell(1);
                cell.setCellValue(key.getKeyWord());

                cell = row.createCell(2);
                cell.setCellValue(key.getCount());

                j++;
            }

            swb.write(new FileOutputStream(new File(fileName)));
            swb.close();
        }catch(Exception e)
        {
            logger.error("生成excel异常",e);
        }
    }



    @Override
    public void writeUTF8(String csvName, List<KeyWord> keyList) {

    }

}

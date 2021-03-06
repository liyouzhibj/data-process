package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.domain.KeyWordPosition;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service("keyPositionWriteToCSV")
public class KeyPositionWriteToCSV implements DataWrite<String, List<KeyWordPosition>, String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String csvName, List<KeyWordPosition> keyWordPositionsList, String charset) {
        BufferedWriter fileWriter = null;
        try {
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(csvName), charset);
            fileWriter = new BufferedWriter(w);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "文件名（含路径）", "行号", "行起始位置", "行结束位置", "关键字"};
            csvWriter.writeNext(head);

            for (KeyWordPosition keyWordPosition : keyWordPositionsList) {
                String[] row = {Long.toString(keyWordPosition.getId()), keyWordPosition.getFile(),
                        Integer.toString(keyWordPosition.getLinenum()),
                        Integer.toString(keyWordPosition.getStart()),
                        Integer.toString(keyWordPosition.getEnd()),
                        keyWordPosition.getKeyWord()};
                csvWriter.writeNext(row);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }

}

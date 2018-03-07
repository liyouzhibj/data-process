package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.domain.KeyWordPosition;
import com.liyouzhi.dataprocess.domain.KeyWordTranslation;
import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service("keyPositionTranslationWriteToCSV")
public class KeyPositionTranslationWriteToCSV implements DataWrite<String, List<KeyWordTranslationPosition>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String csvName, List<KeyWordTranslationPosition> keyWordTranslationPositionList) {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(csvName);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "文件名（含路径）", "行号", "行起始位置", "行结束位置", "关键字", "翻译"};
            csvWriter.writeNext(head);

            for(KeyWordTranslationPosition key : keyWordTranslationPositionList){
                String[] row = {Long.toString(key.getId()), key.getFile(),
                        Integer.toString(key.getLinenum()),
                        Integer.toString(key.getStart()),
                        Integer.toString(key.getEnd()),
                        key.getKeyWord(),
                        key.getKeyWordTranslation()};
                csvWriter.writeNext(row);
            }
        }catch (IOException e){
            logger.error(e.toString());
        }finally {
            try{
                fileWriter.close();
            }catch (IOException e){
                logger.error(e.toString());
            }
        }
    }

    @Override
    public void writeUTF8(String csvName, List<KeyWordTranslationPosition> keyWordTranslationPositionList) {
        BufferedWriter fileWriter = null;
        try{
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(csvName),"utf-8");
            fileWriter = new BufferedWriter(w);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "文件名（含路径）", "行号", "行起始位置", "行结束位置", "关键字", "翻译"};
            csvWriter.writeNext(head);

            for(KeyWordTranslationPosition key : keyWordTranslationPositionList){
                String[] row = {Long.toString(key.getId()), key.getFile(),
                        Integer.toString(key.getLinenum()),
                        Integer.toString(key.getStart()),
                        Integer.toString(key.getEnd()),
                        key.getKeyWord(),
                        key.getKeyWordTranslation()};
                csvWriter.writeNext(row);
            }
        }catch (IOException e){
            logger.error(e.toString());
        }finally {
            try{
                fileWriter.close();
            }catch (IOException e){
                logger.error(e.toString());
            }
        }
    }
}

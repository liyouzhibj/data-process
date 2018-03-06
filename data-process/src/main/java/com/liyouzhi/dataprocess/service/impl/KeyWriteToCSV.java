package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service("keyWriteToCSV")
public class KeyWriteToCSV implements DataWrite<String, List<KeyWord>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String csvName, List<KeyWord> keyList) {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(csvName);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "关键字", "关键字次数"};
            csvWriter.writeNext(head);

            for(KeyWord key : keyList){
                String[] row = {Long.toString(key.getId()), key.getKeyWord(), Integer.toString(key.getCount())};
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
    public void writeUTF8(String csvName, List<KeyWord> keyList) {
        BufferedWriter fileWriter = null;
        try{
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(csvName),"utf-8");
            fileWriter = new BufferedWriter(w);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "关键字", "关键字次数"};
            csvWriter.writeNext(head);

            for(KeyWord key : keyList){
                String[] row = {Long.toString(key.getId()), key.getKeyWord(), Integer.toString(key.getCount())};
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

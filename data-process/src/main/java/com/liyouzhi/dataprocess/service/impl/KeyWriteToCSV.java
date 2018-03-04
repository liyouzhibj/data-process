package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class KeyWriteToCSV implements DataWrite<String, List<KeyWord>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String csvName, List<KeyWord> keyList) {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(csvName);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');
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

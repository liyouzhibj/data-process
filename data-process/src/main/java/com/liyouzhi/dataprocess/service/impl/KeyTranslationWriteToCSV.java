package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.domain.KeyWord;
import com.liyouzhi.dataprocess.domain.KeyWordTranslation;
import com.liyouzhi.dataprocess.service.DataWrite;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service("keyTranslationWriteToCSV")
public class KeyTranslationWriteToCSV implements DataWrite<String, List<KeyWordTranslation>, String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String csvName, List<KeyWordTranslation> keyList, String charset) {
        BufferedWriter fileWriter = null;
        try {
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(csvName), charset);
            fileWriter = new BufferedWriter(w);
            CSVWriter csvWriter = new CSVWriter(fileWriter, ',');

            String[] head = {"序号", "关键字", "关键字次数", "翻译"};
            csvWriter.writeNext(head);

            for (KeyWordTranslation key : keyList) {
                String[] row = {Long.toString(key.getId()), key.getKeyWord(), Integer.toString(key.getCount()), key.getKeyWordTranslation()};
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

package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.service.KeySeekPosithon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class KeySeekPositionImpl implements KeySeekPosithon<Long, File, Integer, Integer>{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Long keySeekPosition(File file, Integer lineNum, Integer lineStart) {
        long result = 0;
        BufferedReader reader = null;
        try {
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String tempString = null;
            int line = 1;

            while ((tempString = reader.readLine()) != null) {
                if(line == lineNum){
                    result += lineStart;
                    return result + lineNum - 1;
                }

                line++;
                result += tempString.length();
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return result + lineNum - 1;
    }
}

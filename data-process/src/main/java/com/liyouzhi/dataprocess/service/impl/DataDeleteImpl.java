package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.service.DataDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class DataDeleteImpl implements DataDelete<String, Integer>{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void deleteKeyWordFromFile(String fileName, Integer lineNum, Integer start, Integer end) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            File file = new File(fileName);
            File tempFile = new File(fileName + ".bak");
            FileReader fileReader = new FileReader(file);
            FileWriter fileWriter = new FileWriter(tempFile);
            reader = new BufferedReader(fileReader);
            writer = new BufferedWriter(fileWriter);
            String tempString = "";
            int line = 1;

            while ((tempString = reader.readLine()) != null) {
                if(line == lineNum){
                    tempString = tempString.substring(0, start) + tempString.substring(end, tempString.length());
//                    logger.info("Delete Key word: " + tempString.substring(start, end - start));
                    writer.write(tempString);
                    writer.newLine();
                    line++;
                    continue;
                }
                writer.write(tempString);
                writer.newLine();
                line++;
            }

            file.delete();
            tempFile.renameTo(new File(fileName));

        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }
}

package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;
import com.liyouzhi.dataprocess.service.DataRead;
import com.liyouzhi.dataprocess.service.FileCharset;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Service
public class DataReadFromFile implements DataRead<File, Integer, String, String, KeyWordTranslationPosition> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<Integer, String> readLine(File file, String charset) {
        int lineNo = 1;
        Map<Integer, String> map = new LinkedHashMap<>();
        try {
            List<String> lines;
            if (charset == null || charset.equals("")) {
                FileCharset fileCharset = new FileCharsetImpl();
                charset = (String) fileCharset.getFileCharset(file);
            }

            lines = FileUtils.readLines(file, charset);
            for (String line : lines) {
                map.put(lineNo++, line);
                logger.info("line no " + (lineNo - 1) + " : " + line);
            }

        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }

        return map;
    }

    @Override
    public List<File> fileRecognition(String path) {
        int folderNum = 0;
        LinkedList<File> folderList = new LinkedList<>();
        LinkedList<File> fileList = new LinkedList<>();
        File file = new File(path);

        if (file.exists()) {
            File[] files = file.listFiles();
            for (File fileIteration : files) {
                if (fileIteration.isDirectory()) {
                    folderList.add(fileIteration);
                    folderNum++;
                } else {
                    fileList.add(fileIteration);
                }
            }

            File fileTemp;
            while (!folderList.isEmpty()) {
                fileTemp = folderList.removeFirst();
                files = fileTemp.listFiles();
                for (File fileIteration : files) {
                    if (fileIteration.isDirectory()) {
                        folderList.add(fileIteration);
                        folderNum++;
                    } else {
                        fileList.add(fileIteration);
                    }
                }
            }
        } else {
            logger.error("file does not exist!");
        }

        logger.info("There are " + folderNum + " folders " + "and " + fileList.size() + " files.");
        return fileList;
    }

    @Override
    public List<File> fileFilter(List<File> fileList, String fileType) {
        if (fileType.equals("")) {
            return fileList;
        }

        List<File> result = new LinkedList<>();
        for (File file : fileList) {
            String fileName = file.getName();
            String type = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileType.equals(type)) {
                result.add(file);
            }
        }

        return result;
    }

    @Override
    public List<KeyWordTranslationPosition> readLineToObject(File file) {
        List<KeyWordTranslationPosition> keyList = new LinkedList<>();
        BufferedReader reader = null;
        try {
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            CSVReader csvReader = new CSVReader(reader);
            String[] tempString = null;
            int line = 2;
            csvReader.readNext();  //Begin linenum 2

            while ((tempString = csvReader.readNext()) != null) {
                KeyWordTranslationPosition key = new KeyWordTranslationPosition();
                key.setId(Long.parseLong(tempString[0]));
                key.setFile(tempString[1]);
                key.setLinenum(Integer.parseInt(tempString[2]));
                key.setStart(Integer.parseInt(tempString[3]));
                key.setEnd(Integer.parseInt(tempString[4]));
                key.setKeyWord(tempString[5]);
                key.setKeyWordTranslation(tempString[6]);
                keyList.add(key);
                line++;
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
        return keyList;
    }


    @Override
    public Map<String, List<KeyWordTranslationPosition>> readLineToMap(File file, String charset) {
        Map<String, List<KeyWordTranslationPosition>> keyList = new HashMap<>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), charset);
            reader = new BufferedReader(in);
            CSVReader csvReader = new CSVReader(reader);
            String[] tempString;
            int line = 2;
            csvReader.readNext();  //Begin linenum 2

            while ((tempString = csvReader.readNext()) != null) {
                KeyWordTranslationPosition key = new KeyWordTranslationPosition();
                key.setId(Long.parseLong(tempString[0]));
                key.setFile(tempString[1]);
                key.setLinenum(Integer.parseInt(tempString[2]));
                key.setStart(Integer.parseInt(tempString[3]));
                key.setEnd(Integer.parseInt(tempString[4]));
                key.setKeyWord(tempString[5]);
                key.setKeyWordTranslation(tempString[6]);
                if (keyList.get(tempString[1]) != null) {
                    keyList.get(tempString[1]).add(key);
                } else {
                    List<KeyWordTranslationPosition> temp = new ArrayList<>();
                    temp.add(key);
                    keyList.put(tempString[1], temp);
                }
                line++;
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
        return keyList;
    }
}

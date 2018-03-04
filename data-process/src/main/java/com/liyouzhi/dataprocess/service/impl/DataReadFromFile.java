package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.service.DataRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataReadFromFile implements DataRead<File, Integer, String, String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<Integer, String> readLine(File file) {
        BufferedReader reader = null;
        Map<Integer, String> map = new ConcurrentHashMap<>();
        try {
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String tempString = null;
            int line = 1;

            while ((tempString = reader.readLine()) != null){
                map.put(line, tempString);
                logger.info("line no " + line + " : " + tempString);
                line++;
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
        finally {
            try{
                reader.close();
            }catch (IOException e){
                logger.error(e.toString());
            }
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
        for(File file : fileList){
            String fileName = file.getName();
            String type = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(type);
            if(fileType.equals(type)){
                fileList.remove(file);
            }
        }

        return fileList;
    }
}

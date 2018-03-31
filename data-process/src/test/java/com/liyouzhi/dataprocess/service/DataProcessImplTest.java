package com.liyouzhi.dataprocess.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProcessImplTest {
    @Autowired
    DataRead dataRead;

    @Autowired
    DataProcess dataProcess;

    @Test
    public void dataProcessImplTest(){

        String path = "/Users/liyouzhi/Documents/code/github/liyouzhi/data-process/data-process/data";

        List<File> fileList =  dataRead.fileRecognition(path);

        for(File file : fileList){
            Map<Integer, String> map = dataRead.readLine(file, "");
            for(Map.Entry entry : map.entrySet()){
                System.out.println("value: " + entry.getValue());
                String filter = (String)dataProcess.annotationFilter(entry.getValue());
                System.out.println(filter);
            }
        }
    }
}

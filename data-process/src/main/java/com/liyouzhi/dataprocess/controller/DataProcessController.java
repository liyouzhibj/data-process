package com.liyouzhi.dataprocess.controller;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;
import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWordPosition;
import com.liyouzhi.dataprocess.service.DataProcess;
import com.liyouzhi.dataprocess.service.DataRead;
import com.liyouzhi.dataprocess.service.DataWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DataProcessController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataRead dataRead;

    @Autowired
    DataProcess dataProcess;

    @Autowired
    DataWrite dataWrite;

    @Value("${data.path}")
    private String dataPath;

    /*
    * Save key words to keyWord.csv and KeyWordPosition.csv
    * */
    @RequestMapping("/saveKeyWordToCSV")
    public void saveKeyWordToCSV() {
        String path = "/Users/liyouzhi/Documents/code/github/liyouzhi/data-process/data-process/data";
        String regex = "[\u4e00-\u9fa5]+";
        List<File> files = dataRead.fileRecognition(path);
        List<KeyWord> keyWords = new ArrayList<>();
        List<KeyWordPosition> keyWordPositions = new ArrayList<>();
        int keyWordCount = 0;
        int keyWordPositionCount = 0;

        for (File file : files) {
            Map<Integer, String> lineData = dataRead.readLine(file);
            for (Map.Entry<Integer, String> entry : lineData.entrySet()) {
                String filterAfter = (String) dataProcess.annotationFilter(entry.getValue());
                List<KeyPosition> keys = dataProcess.getKey(filterAfter, regex);
                for (KeyPosition keyPosition : keys) {
                    KeyWordPosition keyWordPosition = new KeyWordPosition();
                    keyWordPosition.setId(keyWordPositionCount);
                    keyWordPosition.setFile(file.getAbsolutePath());
                    keyWordPosition.setLinenum(entry.getKey());
                    keyWordPosition.setStart(keyPosition.getStart());
                    keyWordPosition.setEnd(keyPosition.getEnd());
                    keyWordPosition.setKeyWord(keyPosition.getKey());

                    int count = 0;

                    for (KeyWord keyWord : keyWords) {
                        if (keyWord.getKeyWord().equals(keyPosition.getKey())) {
                            int index = keyWords.indexOf(keyWord);
                            count = keyWords.get(index).getCount() + 1;
                            keyWords.get(index).setCount(count);
                            break;
                        }
                    }

                    if (count == 0) {
                        KeyWord keyWord = new KeyWord();
                        keyWord.setId(keyWordCount++);
                        keyWord.setKeyWord(keyPosition.getKey());
                        keyWord.setCount(1);
                        keyWords.add(keyWord);
                    }
                }
            }
        }

        if (keyWordCount != 0) {
            dataWrite.write(dataPath + "keyWord.csv", keyWords);
        }

        logger.info("KeyWord count: " + keyWordCount);
    }
}

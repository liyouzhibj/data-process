package com.liyouzhi.dataprocess.controller;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.domain.KeyWord;
import com.liyouzhi.dataprocess.domain.KeyWordPosition;
import com.liyouzhi.dataprocess.domain.KeyWordTranslation;
import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;
import com.liyouzhi.dataprocess.service.DataProcess;
import com.liyouzhi.dataprocess.service.DataRead;
import com.liyouzhi.dataprocess.service.DataWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Qualifier("keyWriteToCSV")
    DataWrite dataWrite_Key;

    @Autowired
    @Qualifier("keyPositionWriteToCSV")
    DataWrite dataWrite_KeyPosition;

    @Autowired
    @Qualifier("keyTranslationWriteToCSV")
    DataWrite dataWrite_KeyTranslation;

    @Autowired
    @Qualifier("keyPositionTranslationWriteToCSV")
    DataWrite dataWrite_KeyPositionTranslation;

    @Value("${data.path}")
    private String dataPath;

    /*
    * Save key words to keyWord.csv and KeyWordPosition.csv
    * */
    @RequestMapping("/saveKeyWordToCSV")
    public String saveKeyWordToCSV(@RequestBody Map<String,Object> requestMap) {
        String path = requestMap.get("path").toString();
        String regex = requestMap.get("regex").toString();
        String fileType = requestMap.get("fileType").toString();

        List<File> fileFilterBefore = dataRead.fileRecognition(path);
        List<File> files = dataRead.fileFilter(fileFilterBefore, fileType);
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
                    keyWordPositions.add(keyWordPosition);
                    keyWordPositionCount++;

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
            dataWrite_Key.write(dataPath + "keyWord.csv", keyWords);
            dataWrite_KeyPosition.write(dataPath + "keyWordPosition.csv", keyWordPositions);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "sucess";
    }

    /*
     * Save key words to keyWordTranslation.csv and KeyWordTranslationPosition.csv
     * */
    @RequestMapping("/saveKeyWordTranslationToCSV")
    public String saveKeyWordTranslationToCSV(@RequestBody Map<String,Object> requestMap) {
        String path = requestMap.get("path").toString();
        String regex = requestMap.get("regex").toString();
        String fileType = requestMap.get("fileType").toString();
        String sourceLang = requestMap.get("sourceLang").toString();
        String targetLang = requestMap.get("targetLang").toString();

        List<File> fileFilterBefore = dataRead.fileRecognition(path);
        List<File> files = dataRead.fileFilter(fileFilterBefore, fileType);
        List<KeyWordTranslation> keyWords = new ArrayList<>();
        List<KeyWordTranslationPosition> keyWordPositions = new ArrayList<>();
        int keyWordCount = 0;
        int keyWordPositionCount = 0;

        for (File file : files) {
            Map<Integer, String> lineData = dataRead.readLine(file);
            for (Map.Entry<Integer, String> entry : lineData.entrySet()) {
                String filterAfter = (String) dataProcess.annotationFilter(entry.getValue());
                List<KeyPosition> keys = dataProcess.getKey(filterAfter, regex);
                for (KeyPosition keyPosition : keys) {
                    KeyWordTranslationPosition keyWordPosition = new KeyWordTranslationPosition();
                    keyWordPosition.setId(keyWordPositionCount);
                    keyWordPosition.setFile(file.getAbsolutePath());
                    keyWordPosition.setLinenum(entry.getKey());
                    keyWordPosition.setStart(keyPosition.getStart());
                    keyWordPosition.setEnd(keyPosition.getEnd());
                    keyWordPosition.setKeyWord(keyPosition.getKey());
                    String translationKey = (String)dataProcess.translationKey(keyPosition.getKey(), sourceLang, targetLang);
                    keyWordPosition.setKeyWordTranslation(translationKey);
                    keyWordPositions.add(keyWordPosition);
                    keyWordPositionCount++;

                    int count = 0;

                    for (KeyWordTranslation keyWord : keyWords) {
                        if (keyWord.getKeyWord().equals(keyPosition.getKey())) {
                            int index = keyWords.indexOf(keyWord);
                            count = keyWords.get(index).getCount() + 1;
                            keyWords.get(index).setCount(count);
                            break;
                        }
                    }

                    if (count == 0) {
                        KeyWordTranslation keyWord = new KeyWordTranslation();
                        keyWord.setId(keyWordCount++);
                        keyWord.setKeyWord(keyPosition.getKey());
                        keyWord.setKeyWordTranslation(translationKey);
                        keyWord.setCount(1);
                        keyWords.add(keyWord);
                    }
                }
            }
        }

        if (keyWordCount != 0) {
            dataWrite_KeyTranslation.write(dataPath + "keyWordTranslation.csv", keyWords);
            dataWrite_KeyPositionTranslation.write(dataPath + "keyWordTranslationPosition.csv", keyWordPositions);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "sucess";
    }
}

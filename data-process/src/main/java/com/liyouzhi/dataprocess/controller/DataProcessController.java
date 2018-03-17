package com.liyouzhi.dataprocess.controller;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.domain.KeyWord;
import com.liyouzhi.dataprocess.domain.KeyWordPosition;
import com.liyouzhi.dataprocess.domain.KeyWordTranslation;
import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;
import com.liyouzhi.dataprocess.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "Data process", description = "Data process")
public class DataProcessController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataRead dataRead;

    @Autowired
    DataProcess dataProcess;

    @Autowired
    KeySeekPosithon keySeekPosithon;

    @Autowired
    DataDelete dataDelete;

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

    @Autowired
    @Qualifier("KeyTranslationWriteToFile")
    DataWrite dataWrite_KeyTranslationWriteToFile;

    @Value("${data.path}")
    private String dataPath;

    @Value("${data.write.charset}")
    private String dataWriteCharset;

    /*
     * Save key words to keyWord.csv and KeyWordPosition.csv
     * */
    @ApiOperation(value = "Save key word to csv file")
    @RequestMapping(value = "/saveKeyWordToCSV", method = RequestMethod.POST)
    public String saveKeyWordToCSV(@RequestBody Map<String, Object> requestMap) {
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
            dataWrite_Key.write(dataPath + "keyWord.csv", keyWords, dataWriteCharset);
            dataWrite_KeyPosition.write(dataPath + "keyWordPosition.csv", keyWordPositions, dataWriteCharset);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "sucess";
    }

    /**
     * Save key words to keyWordTranslation.csv and KeyWordTranslationPosition.csv
     * */
    @ApiOperation(value = "Save key word translation to csv file")
    @RequestMapping(value = "/saveKeyWordTranslationToCSV", method = RequestMethod.POST)
    public String saveKeyWordTranslationToCSV(@RequestBody Map<String, Object> requestMap) {
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
                    String translationKey = (String) dataProcess.translationKey(keyPosition.getKey(), sourceLang, targetLang);
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
            dataWrite_KeyTranslation.write(dataPath + "keyWordTranslation.csv", keyWords, dataWriteCharset);
            dataWrite_KeyPositionTranslation.write(dataPath + "keyWordTranslationPosition.csv", keyWordPositions, dataWriteCharset);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "sucess";
    }

    /**
     * Repalace key word from KeyWordTranslationPosition.csv
     * */
    @ApiOperation(value = "Replace key word from csv file")
    @RequestMapping(value = "/replaceKeyWordFromCSV", method = RequestMethod.POST)
    public String replaceKeyWordFromCSV(@RequestBody Map<String, Object> requestMap){
        String fileName = requestMap.get("fileName").toString();
        List<KeyWordTranslationPosition> keyList = dataRead.readLienToObject(new File(fileName));
        for(KeyWordTranslationPosition key : keyList){
            dataDelete.deleteKeyWordFromFile(key.getFile(), key.getLinenum(), key.getStart(), key.getEnd());
            long seek = (long)keySeekPosithon.keySeekPosition(new File(key.getFile()), key.getLinenum(), key.getStart());
            dataWrite_KeyTranslationWriteToFile.write(key.getFile(), seek, key.getKeyWordTranslation());
        }
        return "sucess";
    }
}

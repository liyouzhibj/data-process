package com.liyouzhi.dataprocess.controller;

import com.liyouzhi.dataprocess.service.impl.FileCharsetImpl;
import com.liyouzhi.dataprocess.vo.*;
import com.liyouzhi.dataprocess.domain.KeyWord;
import com.liyouzhi.dataprocess.domain.KeyWordPosition;
import com.liyouzhi.dataprocess.domain.KeyWordTranslation;
import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;
import com.liyouzhi.dataprocess.service.*;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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

    @Value("${data.write.charset}")
    private String dataWriteCharset;

    @Value("${data.write.charset}")
    private String dataReadCharset;

    /**
     * Save key words to keyWord.csv and KeyWordPosition.csv
     */
    @ApiOperation(value = "保存关键词至CSV文件")
    @RequestMapping(value = "/saveKeyWordToCSV", method = RequestMethod.POST)
    public String saveKeyWordToCSV(@RequestBody SKWTCSVRequest skwtcsvRequest) {
        String path = skwtcsvRequest.getDataPath();
        String resultPath = skwtcsvRequest.getResultPath();
        String regex = skwtcsvRequest.getRegex();
        String fileType = skwtcsvRequest.getFileType();
        String sourceFileCharset = skwtcsvRequest.getSourceFileCharset();

        File fileIsExist = new File(path);
        if (!fileIsExist.exists()) {
            return "Path is not exist!";
        }

        List<File> fileFilterBefore = dataRead.fileRecognition(path);
        List<File> files = dataRead.fileFilter(fileFilterBefore, fileType);
        List<KeyWord> keyWords = new ArrayList<>();
        List<KeyWordPosition> keyWordPositions = new ArrayList<>();
        int keyWordCount = 0;
        int keyWordPositionCount = 0;

        for (File file : files) {
            Map<Integer, String> lineData = dataRead.readLine(file, sourceFileCharset);
            for (Entry<Integer, String> entry : lineData.entrySet()) {
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
            dataWrite_Key.write(resultPath + "keyWord.csv", keyWords, dataWriteCharset);
            dataWrite_KeyPosition.write(resultPath + "keyWordPosition.csv", keyWordPositions, dataWriteCharset);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "sucess";
    }

    /**
     * Save key words to keyWordTranslation.csv and KeyWordTranslationPosition.csv
     */
    @ApiOperation(value = "保存关键词的翻译至CSV文件")
    @RequestMapping(value = "/saveKeyWordTranslationToCSV", method = RequestMethod.POST)
    public String saveKeyWordTranslationToCSV(@RequestBody SKWTTCSVRequest skwttcsvRequest) {
        String path = skwttcsvRequest.getDataPath();
        String resultPath = skwttcsvRequest.getResultPath();
        String regex = skwttcsvRequest.getRegex();
        String fileType = skwttcsvRequest.getFileType();
        String sourceFileCharset = skwttcsvRequest.getSourceFileCharset();
        String sourceLang = skwttcsvRequest.getSourceLang();
        String targetLang = skwttcsvRequest.getTargetLang();

        File fileIsExist = new File(path);
        if (!fileIsExist.exists()) {
            return "Path is not exist!";
        }

        List<File> fileFilterBefore = dataRead.fileRecognition(path);
        List<File> files = dataRead.fileFilter(fileFilterBefore, fileType);
        List<KeyWordTranslation> keyWords = new ArrayList<>();
        Map<String, KeyWordTranslation> KeyCountUtil = new HashMap<>();
        List<KeyWordTranslationPosition> keyWordPositions = new ArrayList<>();
        int keyWordCount = 0;
        int keyWordPositionCount = 0;

        for (File file : files) {
            Map<Integer, String> lineData = dataRead.readLine(file, sourceFileCharset);
            for (Entry<Integer, String> entry : lineData.entrySet()) {
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

                    if (KeyCountUtil.get(keyPosition.getKey()) != null) {
                        KeyWordTranslation temp = KeyCountUtil.get(keyPosition.getKey());
                        temp.setCount(temp.getCount() + 1);
                    } else {
                        KeyCountUtil.put(keyPosition.getKey(), new KeyWordTranslation(keyWordCount++, keyPosition.getKey(), translationKey, 1));
                    }
                }
            }
        }
        for (KeyWordTranslation temp : KeyCountUtil.values()) {
            keyWords.add(temp);
        }
        if (keyWordCount != 0) {
            dataWrite_KeyTranslation.write(resultPath + "keyWordTranslation.csv", keyWords, dataWriteCharset);
            dataWrite_KeyPositionTranslation.write(resultPath + "keyWordTranslationPosition.csv", keyWordPositions, dataWriteCharset);
        }

        logger.info("KeyWord count: " + keyWordCount);
        return "success";
    }


    /**
     * Replace key word from csv file
     */
    @ApiOperation(value = "依据CSV文件替换原文件关键词所对应的翻译")
    @RequestMapping(value = "/replaceKeyWordFromCSV", method = RequestMethod.POST)
    public String replaceKeyWordFromCSV(@RequestBody RKWFCSVRequest rkwfcsvRequest) throws Exception {
        String fileName = rkwfcsvRequest.getFileName();
        String sourceFileCharset = rkwfcsvRequest.getSourceFileCharset();

        File fileIsExist = new File(fileName);
        if (!fileIsExist.exists()) {
            return "Path is not exist!";
        }

        Map<String, List<KeyWordTranslationPosition>> keyList = dataRead.readLineToMap(fileIsExist, dataReadCharset);
        for (Entry<String, List<KeyWordTranslationPosition>> temp : keyList.entrySet()) {
            List<String> lines;
            if (sourceFileCharset == null || sourceFileCharset.equals("")) {
                FileCharset fileCharset = new FileCharsetImpl();
                sourceFileCharset = (String) fileCharset.getFileCharset(new File(temp.getKey()));
            }

            lines = FileUtils.readLines(new File(temp.getKey()), sourceFileCharset);
            for(KeyWordTranslationPosition tempKey : temp.getValue()){
                int lineNum = tempKey.getLinenum() - 1 ;
                logger.info("sourceString: " + lines.get(lineNum));
                String changeKeyToTrans = lines.get(lineNum).
                        replaceFirst(tempKey.getKeyWord(), tempKey.getKeyWordTranslation());
                lines.set(lineNum, changeKeyToTrans);
                logger.info("targetString: " + changeKeyToTrans);
            }


            File fileBak = new File(temp.getKey() + ".bak");
            File sourceFile = new File(temp.getKey());
            FileUtils.writeLines(fileBak, sourceFileCharset, lines, true);
            FileUtils.forceDelete(sourceFile);
            FileUtils.moveFile(fileBak, sourceFile);
        }
        return "success";
    }


    /**
     * Replace key word translation to file
     */
    @ApiOperation(value = "直接替换原文件关键词所对应的翻译")
    @RequestMapping(value = "/translateFileToFile", method = RequestMethod.POST)
    public String translateFileToFile(@RequestBody TFTFRequest tftfRequest) {
        String path = tftfRequest.getDataPath();
        String regex = tftfRequest.getRegex();
        String fileType = tftfRequest.getFileType();
        String sourceLang = tftfRequest.getSourceLang();
        String targetLang = tftfRequest.getTargetLang();
        String sourceFileCharset = tftfRequest.getSourceFileCharset();

        File fileIsExist = new File(path);
        if (!fileIsExist.exists()) {
            return "Path is not exist!";
        }

        List<File> fileFilterBefore = dataRead.fileRecognition(path);
        List<File> files = dataRead.fileFilter(fileFilterBefore, fileType);
        try {
            for (File file : files) {
                if(sourceFileCharset == null || sourceFileCharset.equals("")){
                    FileCharset fileCharset = new FileCharsetImpl();
                    sourceFileCharset = (String)fileCharset.getFileCharset(file);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), sourceFileCharset));
                String temp;
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath() + ".bak"), sourceFileCharset));
                while ((temp = br.readLine()) != null) {
                    String wirteStr = temp;
                    String filterAfter = (String) dataProcess.annotationFilter(temp);
                    List<KeyPosition> keys = dataProcess.getKey(filterAfter, regex);
                    for (KeyPosition key : keys) {
                        String translationKey = (String) dataProcess.translationKey(key.getKey(), sourceLang, targetLang);
                        wirteStr = wirteStr.replaceFirst(key.getKey(), translationKey);
                    }
                    bw.write(wirteStr + "\n");
                }
                br.close();
                bw.close();

                File tempFile = new File(file.getAbsolutePath() + ".bak");
                file.delete();
                tempFile.renameTo(file);
            }
        } catch (Exception e) {
            logger.error("Translate err: ", e);
        }
        logger.info("translate end ");
        return "success";
    }
}

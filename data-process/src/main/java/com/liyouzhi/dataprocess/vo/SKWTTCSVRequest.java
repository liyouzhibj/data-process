package com.liyouzhi.dataprocess.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "/saveKeyWordTranslationToCSV 请求参数")
public class SKWTTCSVRequest {
    @ApiModelProperty(value = "待处理数据路径", example = "/user/data/", notes = "windows机器带盘符", position = 1)
    private String dataPath;
    @ApiModelProperty(value = "处理结果CSV文件存放路径", example = "/user/result/", position = 2)
    private String resultPath;
    @ApiModelProperty(value = "正则表达式", example = "[\u4e00-\u9fa5]+", notes = "程序将检索符合此正则的关键词", position = 3)
    private String regex;
    @ApiModelProperty(value = "文件类型", example = "java", notes = "传空时，检索待处理数据路径下所有文件", position = 4)
    private String fileType;
    @ApiModelProperty(value = "原文件字符类型", example = "UTF-8", notes = "传空时，使用探测器类自动探测文件类型", position = 5)
    private String sourceFileCharset;
    @ApiModelProperty(value = "原语言", example = "zh-CHS", position = 6)
    private String sourceLang;
    @ApiModelProperty(value = "翻译语言", example = "EN", position = 7)
    private String targetLang;

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSourceFileCharset() {
        return sourceFileCharset;
    }

    public void setSourceFileCharset(String sourceFileCharset) {
        this.sourceFileCharset = sourceFileCharset;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }
}

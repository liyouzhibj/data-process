package com.liyouzhi.dataprocess.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "/replaceKeyWordFromCSV 请求参数")
public class RKWFCSVRequest {
    @ApiModelProperty(value = "CSV文件路径", example = "/user/data/keyWordPositionTranslation.csv", notes = "", position = 1)
    private String fileName;
    @ApiModelProperty(value = "原文件字符类型", example = "UTF-8", notes = "传空时，使用探测器类自动探测文件类型", position = 2)
    private String sourceFileCharset;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceFileCharset() {
        return sourceFileCharset;
    }

    public void setSourceFileCharset(String sourceFileCharset) {
        this.sourceFileCharset = sourceFileCharset;
    }
}

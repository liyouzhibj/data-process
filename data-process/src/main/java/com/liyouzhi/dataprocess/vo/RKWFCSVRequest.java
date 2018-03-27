package com.liyouzhi.dataprocess.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "/replaceKeyWordFromCSV 请求参数")
public class RKWFCSVRequest {
    @ApiModelProperty(value = "CSV文件路径", example = "/user/data/keyWordPositionTranslation.csv", notes = "", position = 1)
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

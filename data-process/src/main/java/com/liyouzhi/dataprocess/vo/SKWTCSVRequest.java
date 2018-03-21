package com.liyouzhi.dataprocess.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "/saveKeyWordToCSV 请求参数")
public class SKWTCSVRequest {
    @ApiModelProperty(value = "待处理数据路径", example = "/user/data/", notes = "windows机器带盘符", position = 1)
    private String dataPath;
    @ApiModelProperty(value = "处理结果CSV文件存放路径", example = "/user/result/", position = 2)
    private String resultPath;
    @ApiModelProperty(value = "正则表达式", example = "[\u4e00-\u9fa5]+", notes = "程序将检索符合此正则的关键词", position = 3)
    private String regex;
    @ApiModelProperty(value = "文件类型", example = "java", notes = "传空时，检索待处理数据路径下所有文件", position = 4)
    private String fileType;

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
}

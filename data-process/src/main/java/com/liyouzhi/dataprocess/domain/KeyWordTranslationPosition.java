package com.liyouzhi.dataprocess.domain;

public class KeyWordTranslationPosition {
    public KeyWordTranslationPosition(){}

    private long id;
    private String file;
    private int linenum;
    private int start;
    private int end;
    private String keyWord;
    private String keyWordTranslation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getLinenum() {
        return linenum;
    }

    public void setLinenum(int linenum) {
        this.linenum = linenum;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWordTranslation() {
        return keyWordTranslation;
    }

    public void setKeyWordTranslation(String keyWordTranslation) {
        this.keyWordTranslation = keyWordTranslation;
    }
}

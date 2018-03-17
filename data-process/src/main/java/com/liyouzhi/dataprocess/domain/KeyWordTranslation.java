package com.liyouzhi.dataprocess.domain;

public class KeyWordTranslation {
    public KeyWordTranslation(){}

    private long id;
    private String keyWord;
    private String keyWordTranslation;
    private int count = 1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public KeyWordTranslation(long id, String keyWord, String keyWordTranslation, int count) {
        this.id = id;
        this.keyWord = keyWord;
        this.keyWordTranslation = keyWordTranslation;
        this.count = count;
    }
}

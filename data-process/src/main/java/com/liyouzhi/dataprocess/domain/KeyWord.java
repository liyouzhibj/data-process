package com.liyouzhi.dataprocess.domain;

import javax.persistence.*;

public class KeyWord {
    public KeyWord(){}

    private long id;
    private String keyWord;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

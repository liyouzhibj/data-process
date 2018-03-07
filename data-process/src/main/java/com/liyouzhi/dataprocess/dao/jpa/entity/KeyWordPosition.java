//package com.liyouzhi.dataprocess.dao.jpa.entity;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "key_word_positions")
//public class KeyWordPosition {
//    public KeyWordPosition(){}
//
//    private long id;
//    private String file;
//    private int linenum;
//    private int start;
//    private int end;
//    private String keyWord;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Column(name = "file")
//    public String getFile() {
//        return file;
//    }
//
//    public void setFile(String file) {
//        this.file = file;
//    }
//
//    @Column(name = "linenum")
//    public int getLinenum() {
//        return linenum;
//    }
//
//    public void setLinenum(int linenum) {
//        this.linenum = linenum;
//    }
//
//    @Column(name = "start")
//    public int getStart() {
//        return start;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    @Column(name = "end")
//    public int getEnd() {
//        return end;
//    }
//
//    public void setEnd(int end) {
//        this.end = end;
//    }
//
//    @Column(name = "key_word")
//    public String getKeyWord() {
//        return keyWord;
//    }
//
//    public void setKeyWord(String keyWord) {
//        this.keyWord = keyWord;
//    }
//
//}

//package com.liyouzhi.dataprocess.dao.jpa.entity;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "key_words")
//public class KeyWord {
//    public KeyWord(){}
//
//    private long id;
//    private String keyWord;
//    private int count = 1;
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
//    @Column(name = "key_word", unique = true, nullable = false)
//    public String getKeyWord() {
//        return keyWord;
//    }
//
//    public void setKeyWord(String keyWord) {
//        this.keyWord = keyWord;
//    }
//
//    @Column(name = "count", nullable = false)
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//}

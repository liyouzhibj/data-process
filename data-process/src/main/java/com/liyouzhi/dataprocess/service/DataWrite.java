package com.liyouzhi.dataprocess.service;

import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;

import java.util.List;

public interface DataWrite<T, V> {
    /**
    *Write data 'v' to file 't'
    * */
    void write(T t, V v);

    void writeUTF8(T csvName, V keyList);
}

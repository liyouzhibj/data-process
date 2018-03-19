package com.liyouzhi.dataprocess.service;

import com.liyouzhi.dataprocess.domain.KeyWordTranslationPosition;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DataRead<T, K, V, S, O> {
    /**
    * Read a Line from t, return line number for type K and line value for type V
    * */
    Map<K, V> readLine(T t);


    /**
    * Recognition files in the file path s
    * */
    List<T> fileRecognition(S s);

    /**
    *Filter file for type s
    * */
    List<T> fileFilter(List<T> t, S s);

    /**
     * Read a line from file t to object o
     * */
    List<O> readLineToObject(T t);


    /**
     * Read a line from file t to map
     * */
    Map<String,List<KeyWordTranslationPosition>>  readLineToMap(T t);
}

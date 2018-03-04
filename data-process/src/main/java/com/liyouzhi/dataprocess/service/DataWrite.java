package com.liyouzhi.dataprocess.service;

public interface DataWrite<T, V> {
    /*
    *Write data 'v' to file 't'
    * */
    void write(T t, V v);
}

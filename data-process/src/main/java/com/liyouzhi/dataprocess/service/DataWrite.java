package com.liyouzhi.dataprocess.service;

import java.util.List;

public interface DataWrite<T, V, L> {

    /**
    *Write data 'v' to file 't' use 'l' encode.
    * */
    void write(T t, V v, L l);
}

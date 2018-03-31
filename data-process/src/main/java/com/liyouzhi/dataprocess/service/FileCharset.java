package com.liyouzhi.dataprocess.service;

public interface FileCharset<T, F> {

    /**
     * Get charset for the file f
     * */
    T getFileCharset(F f);
}

package com.liyouzhi.dataprocess.service;

public interface KeySeekPosithon<T, F, K, V> {

    /**
     * Get key seek in file f, according to k and v
     * */
    T keySeekPosition(F f, K k, V v);
}

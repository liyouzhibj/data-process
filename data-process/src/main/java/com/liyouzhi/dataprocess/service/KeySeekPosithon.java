package com.liyouzhi.dataprocess.service;

public interface KeySeekPosithon<T, F, K, V> {
    T keySeekPosition(F f, K k, V v);
}

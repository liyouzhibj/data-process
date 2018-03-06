package com.liyouzhi.dataprocess.service;

import java.util.List;

public interface DataProcess<T, K, V, R> {
    /**
    *Filter annotation in data t
    * */
    T annotationFilter(T t);

    /**
    *Get key list from data 't' where conform to regex 'r'
    * */
    List<K> getKey(T t, R r);

    /**
    *Translate key 'v' from lang 'l' to lang 'p'
    * */
    V translationKey(V v, V l, V p);
}

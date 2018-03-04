package com.liyouzhi.dataprocess.dao;

import java.util.List;

public interface DataDao<T> {
    void save(T t);
    T findByKeyIs(String key);
    List<T> findAll();
}

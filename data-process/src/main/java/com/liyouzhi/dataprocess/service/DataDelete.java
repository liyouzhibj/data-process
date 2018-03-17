package com.liyouzhi.dataprocess.service;

public interface DataDelete<F, I> {
    /**
     * Delete keyword from file f, keyword position is in line l, start s, end e
     * */
    void deleteKeyWordFromFile(F f, I l, I s, I e);
}

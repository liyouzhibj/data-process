package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.service.DataProcess;

import java.util.List;

public class DataProcessImpl implements DataProcess<String, KeyPosition, String, String> {
    @Override
    public String annotationFilter(String sourceData) {
        return null;
    }

    @Override
    public List<KeyPosition> getKey(String sourceData, String regex) {
        return null;
    }

    @Override
    public String translationKey(String key, String lang) {
        return null;
    }
}

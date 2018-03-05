package com.liyouzhi.dataprocess.service;

import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeyWordWriteToCSVTest {
    @Autowired
    @Qualifier("keyWriteToCSV")
    DataWrite dataWrite;

    @Test
    public void keyWriteToCSVTest(){

        List<KeyWord> keys = new ArrayList<>();
        KeyWord key = new KeyWord();
        key.setKeyWord("hello");
        key.setId(1);
        key.setCount(1);
        keys.add(key);
        keys.add(key);

        String csvName = "data/test.csv";
        dataWrite.write(csvName, keys);
    }
}

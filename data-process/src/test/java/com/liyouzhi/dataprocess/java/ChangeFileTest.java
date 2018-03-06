package com.liyouzhi.dataprocess.java;

import com.liyouzhi.dataprocess.util.ChangeFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChangeFileTest {
    @Test
    public void changeFileTest() throws  Exception{
        ChangeFile changeFile = new ChangeFile();
        byte[] bytes = "abcd".getBytes();
        changeFile.changeFile("/Users/liyouzhi/Documents/code/github/liyouzhi/data-process/data-process/data/test2.java", 0, 2, 3, bytes);
    }
}

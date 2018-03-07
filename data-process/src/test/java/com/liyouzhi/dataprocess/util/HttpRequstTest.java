package com.liyouzhi.dataprocess.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpRequstTest {
    @Test
    public void HttpRequstTest() throws Exception{
        HttpRequest httpRequest = new HttpRequest();
        Map<String, String> params = new HashMap<>();
        params.put("userId", "123456");
        params.put("userName", "李晶");
        httpRequest.requestForHttp("http://localhost:8090//postRequestBody", params);
    }
}

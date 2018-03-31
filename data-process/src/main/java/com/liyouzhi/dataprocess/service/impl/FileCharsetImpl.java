package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.service.FileCharset;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileCharsetImpl implements FileCharset<String, File> {
    private static Logger logger = LoggerFactory.getLogger(FileCharsetImpl.class);

    @Override
    public String getFileCharset(File file) {
        byte[] buf = new byte[4096];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        try {
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        detector.dataEnd();

        String encoding = detector.getDetectedCharset();

        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
        } else {
            System.out.println("No encoding detected.");
            encoding = "UTF-8";
        }

        detector.reset();

        logger.info(file.getName() + " charset is: " + encoding);
        return encoding;
    }
}

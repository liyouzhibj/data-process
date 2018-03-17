package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.service.DataWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

@Service("KeyTranslationWriteToFile")
public class KeyTranslationWriteToFile implements DataWrite<String, Long, String>{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(String fileName, Long pos, String content){
        try{
            File file = new File(fileName);
            if (!file.exists() && !file.isDirectory()) {
                file.createNewFile();
                RandomAccessFile r = new RandomAccessFile(new File(fileName), "rw");
                r.seek(0l);
                r.write(content.getBytes());
                return;
            }
            RandomAccessFile r = new RandomAccessFile(new File(fileName), "rw");
            long fileSize = r.length();
            if (pos > fileSize) {
                r.seek(fileSize);
                r.write(content.getBytes());
                return;
            }
            File newFile = new File(fileName + "~");
            RandomAccessFile rtemp = new RandomAccessFile(newFile, "rw");
            FileChannel sourceChannel = r.getChannel();
            FileChannel targetChannel = rtemp.getChannel();
            sourceChannel.transferTo(pos, (fileSize - pos), targetChannel);
            sourceChannel.truncate(pos);
            r.seek(pos);
            r.write(content.getBytes());
            long newOffset = r.getFilePointer();
            targetChannel.position(0L);
            sourceChannel.transferFrom(targetChannel, newOffset, (fileSize - pos));
            sourceChannel.close();
            targetChannel.close();
            newFile.delete();
        }catch (Exception e){
            logger.error(e.toString());
        }

    }
}

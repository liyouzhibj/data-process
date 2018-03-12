package com.liyouzhi.dataprocess.java;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by lks on 2017-03-17.
 */
public class FileWriteHeadAndEnd {
   // public static Logger log = Logger.getLogger(FileWriteHeadAndEnd.class);

    public static void writeHeadAndEnd(String filepath){
        File file = new File(filepath);
        if(!file.exists() && !file.isDirectory()){
            try {
                file.createNewFile();
            } catch (IOException e) {
               // log.error("文件创建失败",e);
            }
        }
        try (RandomAccessFile raw = new RandomAccessFile(file,"rw")){
            long pos = 8l;
            raw.seek(pos);
            String aaa = "文件头第8个位置卖家拒绝和科技是的附属国豆腐干岁的法国的是法国岁的法国是分公司的 ！！！";
            //写文件头时候需要预留占位符
            raw.write(aaa.getBytes());
            raw.seek(raw.length()+aaa.length());
            raw.write("写入文件末尾！！</end>".getBytes());
            raw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
          //  log.error("文件没有发现",e);
        } catch (IOException e) {
            e.printStackTrace();
          //  log.error("Io异常",e);
        }
    }

    public static void main(String[] args) {
        FileWriteHeadAndEnd.writeHeadAndEnd("C:\\D\\abd.txt");
    }


    public static void insertStrToFile(String fileName,long pos,String content) throws Exception
    {
        File file = new File(fileName);
        if(!file.exists() && !file.isDirectory())
        {
            file.createNewFile();
        }

        

    }
}

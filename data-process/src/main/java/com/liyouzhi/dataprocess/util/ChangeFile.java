package com.liyouzhi.dataprocess.util;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ChangeFile {

    public boolean changeFile(String fName, int start, int sourceLength, int targetLength, byte[] targetBytes) throws Exception {
        //创建一个随机读写文件对象
        RandomAccessFile raf = new RandomAccessFile(fName, "rw");
        long totalLen = raf.length();
        System.out.println("文件总长字节是: " + totalLen);
        //打开一个文件通道
        FileChannel channel = raf.getChannel();

        ByteBuffer byteBuffer = MappedByteBuffer.allocate(targetLength);
        //映射文件中的某一部分数据以读写模式到内存中
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, start, sourceLength);
        //示例修改字节
        for (int i = 0; i < targetLength; i++) {
            byte src = targetBytes[i];
            buffer.put(i, src);//修改Buffer中映射的字节的值
            System.out.println("被改为大写的原始字节是:" + src);
        }
        buffer.force();//强制输出,在buffer中的改动生效到文件
        buffer.clear();
        channel.close();
        raf.close();
        return true;
    }

}

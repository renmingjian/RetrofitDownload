package com.js.retrofitdownload.utils;

import com.js.retrofitdownload.bean.DownloadInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;

/**
 * Created by ${R.js} on 2018/3/22.
 */

public class FileUtil {
    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public static void writeCache(ResponseBody responseBody, File file, DownloadInfo info) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getContentLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getContentLength();
        }

        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 4];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}

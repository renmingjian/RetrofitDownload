package com.js.retrofitdownload.http;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 下载的Service
 * Created by ${R.js} on 2018/3/22.
 */

public interface DownLoadService {
    /**
     * @param start 从某个字节开始下载数据
     * @param url   文件下载的url
     * @return Observable
     * @Streaming 这个注解必须添加，否则文件全部写入内存，文件过大会造成内存溢出
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}

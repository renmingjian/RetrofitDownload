package com.js.retrofitdownload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.js.retrofitdownload.http.DownloadManager;
import com.js.retrofitdownload.utils.MyConstants;

public class MainActivity extends AppCompatActivity implements DownloadManager.ProgressListener {

    private ProgressBar pb_progress;
    private TextView tv_progress;
    private DownloadManager downloadManager;
    private int i = 0;
    private Button btn_pasuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        btn_pasuse = (Button) findViewById(R.id.btn_pasuse);

        downloadManager = DownloadManager.getInstance();
        downloadManager.setProgressListener(this);
    }

    /**
     * 点击开始下载
     */
    public void start(View view) {
        downloadManager.start(MyConstants.DOWNLOAD_URL);
    }

    /**
     * 点击暂停下载或继续下载
     */
    public void pasuse(View view) {
        if (i % 2 == 0) {
            downloadManager.pause();
            btn_pasuse.setText("继续下载");
        } else {
            downloadManager.reStart();
            btn_pasuse.setText("暂停下载");
        }
        i++;
    }

    /**
     * 进度回调接口
     */
    @Override
    public void progressChanged(long read, long contentLength, boolean done) {
        final int progress = (int) (100 * read / contentLength);
        pb_progress.setProgress(progress);
        tv_progress.setText(progress + "%");
    }
}

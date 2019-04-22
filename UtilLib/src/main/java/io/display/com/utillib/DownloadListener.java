package io.display.com.utillib;

/**
 * callBack
 * @author Elephant
 * @date created at 2019/4/22 10:28 PM
*/
public interface DownloadListener {

    void onStart();
    void onProgress(FileInfo fileInfo);
    void onFinish(FileInfo FileInfo);
    void onCancled();
}

package io.display.com.utillib;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class FileLoader extends AsyncTask<Void, FileInfo, FileInfo> {

    private static final int TIMEOUT_INTERVAL = 3 * 1000;

    private String mDownloadUrl;
    private final String mDstPath;
    private final String mFileName;
    private final DownloadListener mDownloadListener;

    public FileLoader(String downloadUrl, String dstPath, String fileName, DownloadListener downloadListener) {
        mDownloadUrl = downloadUrl;
        mDstPath = dstPath;
        mFileName = fileName;
        mDownloadListener = downloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDownloadListener.onStart();
    }

    @Override
    protected FileInfo doInBackground(Void... params) {
        URL url;
        FileInfo fileInfo = null;
        int contentLength;
        int downloadLength = 0;
        OutputStream output;
        InputStream istream;
        try {
            url = new URL(mDownloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //timeout
            connection.setConnectTimeout(TIMEOUT_INTERVAL);
            contentLength = connection.getContentLength();
            Log.i(TAG, "doInBackground: contentLength=" + contentLength);
            //get inputStream
            istream = connection.getInputStream();
            File dir = new File(mDstPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //save path
            File file = new File(mDstPath + mFileName);

            output = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 4];
            int count = 0;
            int len = -1;
            while ((len = istream.read(buffer)) != -1) {
                output.write(buffer, 0, len);
                downloadLength += len;

                if (count == 10) {
                    fileInfo = new FileInfo(contentLength, downloadLength, file, file.getPath
                            (), file.getName());
                    publishProgress(fileInfo);
                    count = 0;
                }
                count++;

            }
            fileInfo = new FileInfo(contentLength, downloadLength, file, file.getPath(), file
                    .getName());
            publishProgress(fileInfo);
            output.flush();
            output.close();
            istream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    @Override
    protected void onProgressUpdate(FileInfo... values) {
        super.onProgressUpdate(values);
        mDownloadListener.onProgress(values[0]);
    }

    @Override
    protected void onPostExecute(FileInfo fileInfo) {
        super.onPostExecute(fileInfo);
        mDownloadListener.onFinish(fileInfo);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mDownloadListener.onCancled();
    }
}

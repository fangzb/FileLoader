package io.display.com.utillib;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class FileLoader {

    private static final int TIMEOUT_INTERVAL = 3 * 1000;

    public FileLoader() {
    }

    /**
     * receive a URI to a file, download and save the file to a path
     * @param urlStr
     * @param fileName
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //timeout
        conn.setConnectTimeout(TIMEOUT_INTERVAL);
        //get inputStream
        InputStream inputStream = conn.getInputStream();
        //get array
        byte[] getData = readInputStream(inputStream);
        //save path
        String dirName = Environment.getExternalStorageDirectory() + "/MyDownLoad/";
        File saveDir = new File(dirName);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");
    }

    /**
     * read InputStream
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}

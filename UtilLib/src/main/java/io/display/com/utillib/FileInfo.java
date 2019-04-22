package io.display.com.utillib;

import java.io.File;

/**
 * Custom File Object
 *
 * @author Elephant
 * @date created at 2019/4/22 10:33 PM
 */
public class FileInfo {

    private long mLength;
    private long mDownloadLength;
    private File mFile;
    private String mPath;
    private String mName;

    public FileInfo(long length, long downloadLength, File file, String path, String name) {
        mLength = length;
        mDownloadLength = downloadLength;
        mFile = file;
        mPath = path;
        mName = name;
    }

    public long getLength() {
        return mLength;
    }

    public void setLength(long mLength) {
        this.mLength = mLength;
    }

    public long getDownloadLength() {
        return mDownloadLength;
    }

    public void setDownloadLength(long mDownloadLength) {
        this.mDownloadLength = mDownloadLength;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File mFile) {
        this.mFile = mFile;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}

package com.display.io;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import io.display.com.utillib.DownloadListener;
import io.display.com.utillib.FileLoader;
import io.display.com.utillib.FileInfo;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, DownloadListener {

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static int REQUEST_PERMISSION_CODE = 0x01;

    private EditText etInput;
    private Button btnDownLoad;
    private TextView tvStatus;
    private ProgressBar mProgressBar;

    private FileLoader mDownloadTask;
    private String mDstPath = Environment.getExternalStorageDirectory().getPath() + "/MyDownLoad/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        etInput = findViewById(R.id.et_input);
        btnDownLoad = findViewById(R.id.btn_download);
        tvStatus = findViewById(R.id.tv_status);
        mProgressBar = findViewById(R.id.progressBar);

        btnDownLoad.setOnClickListener(this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "request permission：" + permissions[i] + ",result：" + grantResults[i]);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                mDownloadTask = new FileLoader(etInput.getText().toString().trim(), mDstPath, "Display.io_Test.apk", this);
                mDownloadTask.execute();
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        tvStatus.setText("Begin Download");
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }

    @Override
    public void onProgress(FileInfo fileInfo) {
        if (fileInfo != null) {
            mProgressBar.setMax((int) fileInfo.getLength());
            mProgressBar.setProgress((int) fileInfo.getDownloadLength());
            String mDownText = fileInfo.getFile().getName() + " download " + fileInfo.getDownloadLength() + "kb," + "Total Length is :" +
                    fileInfo.getLength() + "kb";
            tvStatus.setText(mDownText);
        }
    }

    @Override
    public void onFinish(FileInfo FileInfo) {
        Toast.makeText(this, "download success", Toast.LENGTH_SHORT).show();
        tvStatus.setText("download success");
    }

    @Override
    public void onCancled() {
        // cancled
    }
}

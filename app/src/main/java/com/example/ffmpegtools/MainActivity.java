package com.example.ffmpegtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String[] mPermission =  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, mPermission, 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                // 1001的请求码对应的是申请打电话的权限
                // 判断是否同意授权，PERMISSION_GRANTED 这个值代表的是已经获取了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "你同意授权了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "你不同意授权", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    /**
     * 视频转码 flv->mp4.
     * @param view
     */
    public void videoTransform(View view) {
        String inputPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/58.flv";
        String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/60.mp4";
//        String outputPath = getCacheDir().getAbsolutePath()+"/60.mp4";
        File input =new File(inputPath);
        if(!input.exists()){
            Toast.makeText(MainActivity.this, "/Download/58.flv not found!", Toast.LENGTH_LONG).show();
            return;
        }
        File output =new File(outputPath);
        if(output.exists()){
            output.delete();
        }
        //cmds for ffmpeg flv->mp4.
//        String[] commands = FFmpegFactory.buildFlv2Mp4(inputPath,outputPath);
//        inputPath ="rtsp://47.108.81.159:5555/rtsp/00716e06-e3bf-4a53-bd2e-96e3256f96f8";
        inputPath = "http://116.62.177.94:8500/FLV_001410217220_e4f14c192079_20200518152432.flv";
        String[] commands = FFmpegFactory.buildRtsp2Mp4(inputPath,outputPath);

        FFmpegCmd.exec(commands, new FFmpegCmd.OnCmdExecListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onComplete() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "transcode successful!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onProgress(float progress) {

            }
        });
    }

    /**
     * 停止命令.
     * @param view
     */
    public void stopRun(View view) {
        FFmpegCmd.exit();
    }
}

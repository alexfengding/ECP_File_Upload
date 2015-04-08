package com.example.winds_000.fileupload;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;


public class CameraFileActivity extends ActionBarActivity {


    private Button btnCaptureImage, btnRecordVideo;
    private ImageView mImageView;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_file);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mVideoView = (VideoView) findViewById(R.id.videoView);

        btnCaptureImage = (Button) findViewById(R.id.btnCaptureImg);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(captureImage);

            }
        });

        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(recordVideo);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_upload, menu);
        return true;
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.winds_000.fileupload;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    private ImageButton uploadBtn,dbxBtn,onedrivebtn,boxBtn,camBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();

    }

    private void setupView(){
        uploadBtn = (ImageButton) findViewById(R.id.fileUploadButton);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileUploadIntent = new Intent(MainActivity.this, FileUploadActivity.class);
                startActivity(fileUploadIntent);
            }
        });

        camBtn = (ImageButton) findViewById(R.id.camerasButton);
        camBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camIntent = new Intent(MainActivity.this, CameraFileActivity.class);
                startActivity(camIntent);
            }
        });

        dbxBtn = (ImageButton) findViewById(R.id.dropboxButton);
        dbxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbxIntent = new Intent(MainActivity.this, DropboxActivity.class);
                startActivity(dbxIntent);
            }
        });

        onedrivebtn = (ImageButton) findViewById(R.id.onedriveButton);
        onedrivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onedriveIntent = new Intent(MainActivity.this, OnedriveActivity.class);
                startActivity(onedriveIntent);
            }
        });

        boxBtn = (ImageButton) findViewById(R.id.boxButton);
        boxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boxIntent = new Intent(MainActivity.this, BoxActivity.class);
                startActivity(boxIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

package com.example.winds_000.fileupload;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dropbox.chooser.android.DbxChooser;

import java.util.Map;


public class DropboxActivity extends ActionBarActivity {

    static final String APP_KEY = "etskrs21b7vcv3j";
    static final int DBX_CHOOSER_REQUEST = 0;

    private Button mChooserButton;
    private DbxChooser mChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);

        mChooser = new DbxChooser(APP_KEY);

        mChooserButton = (Button) findViewById(R.id.chooser_button);
        mChooserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbxChooser.ResultType resultType;
                switch (((RadioGroup) findViewById(R.id.link_type)).getCheckedRadioButtonId()) {
                    case R.id.link_type_direct: resultType = DbxChooser.ResultType.DIRECT_LINK; break;
                    case R.id.link_type_content: resultType = DbxChooser.ResultType.FILE_CONTENT; break;
                    case R.id.link_type_preview: resultType = DbxChooser.ResultType.PREVIEW_LINK; break;
                    default: throw new RuntimeException("unexpected link type!!");
                }

                mChooser.forResultType(resultType)
                        .launch(DropboxActivity.this, DBX_CHOOSER_REQUEST);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DBX_CHOOSER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DbxChooser.Result result = new DbxChooser.Result(data);
                Log.d("main", "Link to selected file: " + result.getLink());

                showLink(R.id.uri, result.getLink());
                ((TextView) findViewById(R.id.filename)).setText(result.getName().toString(), TextView.BufferType.NORMAL);
                ((TextView) findViewById(R.id.size)).setText(String.valueOf(result.getSize()), TextView.BufferType.NORMAL);
                showLink(R.id.icon, result.getIcon());

                Map<String, Uri> thumbs = result.getThumbnails();
                showLink(R.id.thumb64, thumbs.get("64x64"));
                showLink(R.id.thumb200, thumbs.get("200x200"));
                showLink(R.id.thumb640, thumbs.get("640x480"));
            } else {
                // Failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showLink(int id, Uri uri) {
        TextView v = (TextView) findViewById(id);
        if (uri == null) {
            v.setText("", TextView.BufferType.NORMAL);
            return;
        }
        v.setText(uri.toString(), TextView.BufferType.NORMAL);
        v.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dropbox, menu);
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

package com.example.winds_000.fileupload;

import com.microsoft.onedrivesdk.picker.*;

import android.app.*;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import java.io.InputStream;
import java.net.URL;


public class OnedriveActivity extends ActionBarActivity {

    private static final String ONEDRIVE_CLIENT_ID = "4815282A";



    private final OnClickListener mStartPickingListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            //Reset the form for new entry
            clearResultTable();

            //Determine the link type that was selected
            LinkType linkType;
            if(((RadioButton) findViewById(R.id.radioWebViewLink)).isChecked()){
                linkType = LinkType.WebViewLink;
            } else if (((RadioButton) findViewById(R.id.radioDownloadLink)).isChecked()){
                linkType = LinkType.DownloadLink;
            } else {
                throw new RuntimeException("Invalid Radio Button selection.");
            }

            mPicker.startPicking((Activity)v.getContext(),linkType);
        }
    };



    /**
     * Saves the picked file from OneDrive to the device
     */
    private final OnClickListener mSaveLocally = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (mDownloadUrl == null) {
                return;
            }

            final DownloadManager downloadManager = (DownloadManager)v.getContext().getSystemService(DOWNLOAD_SERVICE);
            final DownloadManager.Request request = new DownloadManager.Request(mDownloadUrl);
            request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
        }
    };

    private IPicker mPicker;

    private Uri mDownloadUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onedrive);

        mPicker = Picker.createPicker(ONEDRIVE_CLIENT_ID);

        (findViewById(R.id.startPickerButton)).setOnClickListener(mStartPickingListener);
        (findViewById(R.id.saveAsButton)).setOnClickListener(mSaveLocally);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // Get the results from the from the picker
        final IPickerResult result = mPicker.getPickerResult(requestCode, resultCode, data);

        // Handle the case if nothing was picked
        if (result == null) {
            Toast.makeText(this, "Did not get a file from the picker!", Toast.LENGTH_LONG).show();
            return;
        }

        // Update the UI with the picker results
        updateResultTable(result);
    }

    /**
     * Updates the results table with details from an {@link IPickerResult}
     *
     * @param result The results of the picker
     */
    private void updateResultTable(final IPickerResult result) {
        ((TextView)findViewById(R.id.nameResult)).setText(result.getName());
        ((TextView)findViewById(R.id.linkTypeResult)).setText(result.getLinkType() + "");
        ((TextView)findViewById(R.id.linkResult)).setText(result.getLink() + "");
        ((TextView)findViewById(R.id.fileSizeResult)).setText(result.getSize() + "");

        final Uri thumbnailSmall = result.getThumbnailLinks().get("small");
        createUpdateThumbnail((ImageView)findViewById(R.id.thumbnail_small), thumbnailSmall).execute((Void)null);
        ((TextView)findViewById(R.id.thumbnail_small_uri)).setText(thumbnailSmall + "");

        final Uri thumbnailMedium = result.getThumbnailLinks().get("medium");
        createUpdateThumbnail((ImageView)findViewById(R.id.thumbnail_medium), thumbnailMedium).execute((Void)null);
        ((TextView)findViewById(R.id.thumbnail_medium_uri)).setText(thumbnailMedium + "");

        final Uri thumbnailLarge = result.getThumbnailLinks().get("large");
        createUpdateThumbnail((ImageView)findViewById(R.id.thumbnail_large), thumbnailLarge).execute((Void)null);
        ((TextView)findViewById(R.id.thumbnail_large_uri)).setText(thumbnailLarge + "");

        findViewById(R.id.thumbnails).setVisibility(View.VISIBLE);

        if (result.getLinkType() == LinkType.DownloadLink) {
            findViewById(R.id.saveAsArea).setVisibility(View.VISIBLE);
            mDownloadUrl = result.getLink();
        }
    }

    /**
     * Clears out all picker results
     */
    private void clearResultTable() {
        ((TextView)findViewById(R.id.nameResult)).setText("");
        ((TextView)findViewById(R.id.linkTypeResult)).setText("");
        ((TextView)findViewById(R.id.linkResult)).setText("");
        ((TextView)findViewById(R.id.fileSizeResult)).setText("");
        findViewById(R.id.thumbnails).setVisibility(View.INVISIBLE);
        findViewById(R.id.saveAsArea).setVisibility(View.INVISIBLE);
        ((ImageView)findViewById(R.id.thumbnail_small)).setImageBitmap(null);
        ((TextView)findViewById(R.id.thumbnail_small_uri)).setText("");
        ((ImageView)findViewById(R.id.thumbnail_medium)).setImageBitmap(null);
        ((TextView)findViewById(R.id.thumbnail_medium_uri)).setText("");
        ((ImageView)findViewById(R.id.thumbnail_large)).setImageBitmap(null);
        ((TextView)findViewById(R.id.thumbnail_large_uri)).setText("");
        mDownloadUrl = null;
    }

    /**
     * Download the thumbnails for display
     *
     * @param uri The uri of the bitmap to retrieve from OneDrive
     * @return The image as a bitmap
     */
    private Bitmap getBitmap(final Uri uri) {
        try {
            if (uri == null) {
                return null;
            }

            final URL url = new URL(uri.toString());
            final InputStream inputStream = url.openConnection().getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Creates a task that will update a thumbnail
     *
     * @param imageView The image view that should be updated
     * @param imageSource The uri of the image that should be put on the image
     *            view
     * @return The task that will perform this update
     */
    private AsyncTask<Void, Void, Bitmap> createUpdateThumbnail(final ImageView imageView, final Uri imageSource) {
        return new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(final Void... params) {
                return getBitmap(imageSource);
            }

            @Override
            protected void onPostExecute(final Bitmap result) {
                imageView.setImageBitmap(result);
            }
        };
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_onedrive, menu);
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

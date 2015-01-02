package com.jsorrell.topicsgame;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class ViewImageActivity extends ActionBarActivity {
    private int submissionId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        submissionId = intent.getIntExtra("submissionId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_specific_submission, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        RestClient.getSpecificSubmission(
                myId,
                submissionId,
                new FileAsyncHttpResponseHandler(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        Fragment submissionViewer;
                        InputStream filestream;
                        try {
                            filestream = new FileInputStream(response);
                        } catch (FileNotFoundException e) {
                            Log.e("Error:", "File Not Found");
                            return;
                        }

                        Bitmap bitmap = BitmapFactory.decodeStream(filestream);
                        ImageView imageViewer = (ImageView)findViewById(R.id.image_viewer);
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            imageViewer.setBackgroundDrawable(new BitmapDrawable(bitmap));
                        } else {
                            imageViewer.setBackground(new BitmapDrawable(bitmap))   ;
                        }
                    }

                    public void onFailure(int statusCode,
                                          org.apache.http.Header[] headers,
                                          java.lang.Throwable throwable,
                                          java.io.File file)
                                    {
                        return;
                    }
                }
        );
    }
}

package com.jsorrell.topicsgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;


public class ViewVideoActivity extends ActionBarActivity {
    VideoView videoview;
    private int submissionId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        submissionId = intent.getIntExtra("submissionId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        videoview = (VideoView) findViewById(R.id.video_viewer);
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(ViewVideoActivity.this);
            mediacontroller.setAnchorView(videoview);

            String videoURL = "http://192.168.1.4:8080/api/" + "submissions/" +
                              Integer.toString(submissionId);

            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_video, menu);
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
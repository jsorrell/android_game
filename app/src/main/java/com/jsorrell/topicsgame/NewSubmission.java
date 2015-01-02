package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.InputStream;


public class NewSubmission extends ActionBarActivity {
    private int gameId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        gameId = intent.getIntExtra("gameId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_submission);
        //read which topic the activity was started for
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_submission, menu);
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

    public void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        //TODO: implement a way to allow user to select submission
        InputStream data = getResources().openRawResource(R.raw.test);

        Log.d("Info", "Sending Submission");
        RestClient.uploadSubmission(myId, gameId, new JsonHttpResponseHandler(), data);
    }
}

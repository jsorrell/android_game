package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;


public class SubmissionActivity extends ActionBarActivity {
    private int gameId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        gameId = intent.getIntExtra("gameId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submission, menu);
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

    public void gotoNewSubmission(View view) {
        Intent intent = new Intent(this, NewSubmission.class);
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }

    public void gotoViewSubmissions(View view) {
        Intent intent = new Intent(this, ViewSubmissionsActivity.class);
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }
}

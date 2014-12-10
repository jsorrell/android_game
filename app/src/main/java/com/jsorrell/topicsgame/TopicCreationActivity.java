package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TopicCreationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_creation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_creation, menu);
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

    public void createTopic(View view){
        ArrayList<Integer> userIds = new ArrayList();
        userIds.add(1);
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                                                             Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        RequestParams params = new RequestParams();
        params.put("topic", ((EditText)findViewById(R.id.topic_field)).getText());
        params.put("creator", myId);
        params.put("players", userIds);
        RestClient.post("/users/" + Integer.toString(myId) + "/games",
                        params,
                        new JsonHttpResponseHandler() {});
    }
}

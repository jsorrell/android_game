package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.Intent;
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
    private ArrayList<Integer> userIds = new ArrayList<>();
    static final int SELECT_FRIENDS_REQUEST = 1;

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
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                                                             Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        RestClient.sendTopicCreationRequestAsync(myId,((EditText)findViewById(R.id.topic_field)).getText().toString(),userIds, new JsonHttpResponseHandler());
    }


    public void selectFriends(View view){
        Intent selectFriendsIntent = new Intent(this, FriendsActivity.class);
        selectFriendsIntent.putExtra("purpose", "selectFriends");
        startActivityForResult(selectFriendsIntent, SELECT_FRIENDS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_FRIENDS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                userIds = data.getIntegerArrayListExtra("selectedFriends");
            }
        }
    }
}

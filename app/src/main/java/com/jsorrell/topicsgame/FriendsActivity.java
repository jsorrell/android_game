package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FriendsActivity extends ActionBarActivity {

    private static final String TAG = "FriendsActivity";
    private ArrayList<Integer> friendList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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

    public class FriendListResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                JSONArray result = response.getJSONArray("friendList");
                for (int i = 0; i < result.length(); i++) {
                    Log.d("Length", Integer.toString(result.length()));
                    FriendsActivity.this.friendList.add(result.getInt(i));
                }

                ListView lv = (ListView) findViewById(R.id.friends_list_view);
                ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(
                        FriendsActivity.this,
                        android.R.layout.simple_list_item_1,
                        FriendsActivity.this.friendList);

                lv.setAdapter(arrayAdapter);

            } catch (JSONException e){
                Log.v("EXCEPTION", e.toString());
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        RestClient.get("user/" + myId + "/friendList", null, new FriendListResponseHandler());
    }

    public void findFriendPressed(View view) {
    }
}

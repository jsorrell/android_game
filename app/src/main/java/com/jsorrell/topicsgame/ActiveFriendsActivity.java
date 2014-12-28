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
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ActiveFriendsActivity extends ActionBarActivity {
    private static final String TAG = "ActiveFriendsActivity";
    private JSONArray friendList = new JSONArray();
    private ArrayList<Integer>selectedFriends = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_friends);
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
                ActiveFriendsActivity.this.friendList = response.getJSONArray("friendList");

                ListView lv = (ListView) findViewById(R.id.friends_list_view);
                final JSONListAdapter listAdapter = new FriendsListAdapter(
                        ActiveFriendsActivity.this,
                        R.layout.friend_list_item,
                        ActiveFriendsActivity.this.friendList);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        try {
                            int userId = listAdapter.getItem(position).getInt("userId");
                            selectedFriends.add(userId);
                        } catch (JSONException e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                });

                lv.setAdapter(listAdapter);

            } catch (JSONException e){
                Log.e("EXCEPTION", e.toString());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);

        RestClient.getFriendListAsync(myId, new FriendListResponseHandler());
    }

    public void findFriendPressed(View view) {
    }

    public void finishSelectingFriends(View view){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("selectedFriends", selectedFriends);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
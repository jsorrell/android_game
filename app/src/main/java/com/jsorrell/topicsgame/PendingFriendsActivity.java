package com.jsorrell.topicsgame;

import android.content.Context;
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


public class PendingFriendsActivity extends ActionBarActivity {
    private JSONArray pendingFriends;
    private int selectedFriend;
    private JSONListAdapter pendingFriendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_friends);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending_friends, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame",
                Context.MODE_PRIVATE);
        int myId = prefs.getInt("userId", -1);
        RestClient.getPendingFriendsAsync(myId, new PendingFriendsResponseHandler());
    }

    public class PendingFriendsResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                ListView lv = (ListView) findViewById(R.id.pending_friends_list_view);
                PendingFriendsActivity.this.pendingFriends = response.getJSONArray("pendingFriends");
                PendingFriendsActivity.this.pendingFriendsAdapter = new FriendsListAdapter(
                        PendingFriendsActivity.this,
                        R.layout.friend_list_item,
                        PendingFriendsActivity.this.pendingFriends);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        try {
                            PendingFriendsActivity.this.selectedFriend = position;

                            int potentialFriendId =
               PendingFriendsActivity.this.pendingFriendsAdapter.getItem(position).getInt("userId");

                            SharedPreferences prefs =
                                    PendingFriendsActivity.this.getSharedPreferences(
                                                                          "com.jsorrell.topicsgame",
                                                                           Context.MODE_PRIVATE);
                            int myId = prefs.getInt("userId", -1);
                            RestClient.acceptFriendRequest(myId,
                                                           potentialFriendId,
                                                           new AcceptFriendResponseHandler());
                        } catch (JSONException e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                });

                lv.setAdapter(PendingFriendsActivity.this.pendingFriendsAdapter);

            } catch (JSONException e){
                Log.e("EXCEPTION", e.toString());
            }
        }
    }

    public class AcceptFriendResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if (response.getString("status") == "success") {
                    PendingFriendsActivity.this.pendingFriends.remove(
                                                        PendingFriendsActivity.this.selectedFriend);
                }

                PendingFriendsActivity.this.pendingFriendsAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.e("EXCEPTION", e.toString());
            }
        }
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

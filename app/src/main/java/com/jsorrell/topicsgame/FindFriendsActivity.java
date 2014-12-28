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
import android.widget.SearchView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class FindFriendsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        SearchView friendSearch = (SearchView)findViewById(R.id.search_friend);
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
        {
            public boolean onQueryTextChange(String newText)
            {

                return true;
            }

            public boolean onQueryTextSubmit(String query)
            {
                RestClient.searchFriend(query, new FriendSearchResponseHandler());
                return true;

            }
        };
        friendSearch.setOnQueryTextListener(queryTextListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_friends, menu);
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

    public class FriendSearchResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                ListView lv = (ListView) findViewById(R.id.results_list_view);
                Log.d("heey", response.getJSONArray("results").getJSONObject(0).getString("firstName"));
                final JSONListAdapter listAdapter = new FriendsListAdapter(
                        FindFriendsActivity.this,
                        R.layout.friend_list_item,
                        response.getJSONArray("results"));

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        try {
                            SharedPreferences prefs =
                            FindFriendsActivity.this.getSharedPreferences("com.jsorrell.topicsgame",
                                    Context.MODE_PRIVATE);
                            int myId = prefs.getInt("userId", -1);
                            int userId = listAdapter.getItem(position).getInt("userId");
                            RestClient.sendFriendRequest(myId,
                                    userId,
                                    new sendFriendRequestResponseHandler());
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

    public class sendFriendRequestResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("send", "notacceptedgames");
            finish();
        }
    }
}

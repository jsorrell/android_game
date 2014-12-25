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


public class ActiveGamesActivity extends ActionBarActivity {
    private JSONArray gamesList = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_games);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending_games, menu);
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
        RestClient.getActiveGamesListAsync(myId, new GamesListResponseHandler());
    }

    public class GamesListResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                ActiveGamesActivity.this.gamesList = response.getJSONArray("games");

                ListView lv = (ListView) findViewById(R.id.games_list_view);
                final JSONListAdapter gamesAdapter = new GamesListAdapter(
                        ActiveGamesActivity.this,
                        R.layout.game_list_item,
                        ActiveGamesActivity.this.gamesList);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        try {
                            int gameId = gamesAdapter.getItem(position).getInt("gameId");
                        } catch (JSONException e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                });

                lv.setAdapter(gamesAdapter);

            } catch (JSONException e){
                Log.e("EXCEPTION", e.toString());
            }
        }
    }
}

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


public class ViewSubmissionsActivity extends ActionBarActivity {
    private int gameId = 0;
    JSONArray submissionsList = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        gameId = intent.getIntExtra("gameId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submissions);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_submissions, menu);
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
        RestClient.getSubmissions(myId, gameId, new SubmissionListResponseHandler());
    }

    public class SubmissionListResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                ViewSubmissionsActivity.this.submissionsList = response.getJSONArray("submissions");

                ListView lv = (ListView) findViewById(R.id.submissions_list_view);

                final JSONListAdapter submissionsAdapter = new SubmissionsListAdapter(
                                                      ViewSubmissionsActivity.this,
                                                      R.layout.submission_list_item,
                                                      ViewSubmissionsActivity.this.submissionsList);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        try {
                            int submissionId =
                                        submissionsAdapter.getItem(position).getInt("submissionId");
                            //case on type of file

                            //image
                            Intent intent = new Intent(ViewSubmissionsActivity.this,
                                                       ViewVideoActivity.class);

                            //video
                            intent.putExtra("submissionId", submissionId);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                });

                lv.setAdapter(submissionsAdapter);

            } catch (JSONException e){
                Log.e("EXCEPTION", e.toString());
            }
        }
    }
}
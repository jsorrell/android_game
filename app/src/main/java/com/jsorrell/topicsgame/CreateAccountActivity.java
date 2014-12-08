package com.jsorrell.topicsgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class CreateAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

    public void createAccount(View view) {
        RequestParams params = new RequestParams();
        SharedPreferences prefs = this.getSharedPreferences("com.jsorrell.topicsgame", Context.MODE_PRIVATE);
        String firstName = ((EditText)findViewById(R.id.first_name)).getText().toString();
        params.put("firstName", firstName);
        prefs.edit().putString("firstName",firstName);
        String lastName = ((EditText)findViewById(R.id.last_name)).getText().toString();
        params.put("lastName", lastName);
        prefs.edit().putString("lastName",lastName);
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        params.put("email", email);
        prefs.edit().putString("email",email);
        RestClient.post("users/", params, new JsonHttpResponseHandler() {});
    }
}

package com.jsorrell.topicsgame;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carl on 12/24/14.
 */
public class FriendsListAdapter extends JSONListAdapter {
    public FriendsListAdapter(Activity activity, int entry_view_id, JSONArray jsonArray) {
        super(activity, entry_view_id, jsonArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(entry_view_id, null);
        }

        JSONObject json_data = getItem(position);
        TextView t1 = (TextView)convertView.findViewById(R.id.friend_list_item);

        if(null!=json_data){
            try {
                t1.setText(this.jsonArray.getJSONObject(position).getString("firstName") +
                           " " +
                           this.jsonArray.getJSONObject(position).getString("lastName"));
            } catch (JSONException e) {
                Log.e("Exception", e.toString());
            }
        }

        return convertView;
    }
}

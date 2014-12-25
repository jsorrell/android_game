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
public class GamesListAdapter extends JSONListAdapter {
    public GamesListAdapter(Activity activity, int entry_view_id, JSONArray jsonArray) {
        super(activity, entry_view_id, jsonArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.activity.getLayoutInflater().inflate(super.entry_view_id, null);
        }

        TextView text = (TextView)convertView.findViewById(R.id.game_list_item);

        JSONObject json_data = getItem(position);
        if(null!=json_data){
            try {
                text.setText(this.jsonArray.getJSONObject(position).getString("gameTopic"));
            } catch (JSONException e) {
                Log.e("Exception", e.toString());
            }
        }

        return convertView;
    }
}

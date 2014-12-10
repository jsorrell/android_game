package com.jsorrell.topicsgame;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carl on 12/9/14.
 */

class JSONListAdapter extends BaseAdapter implements ListAdapter {

    private final Activity activity;
    private final JSONArray jsonArray;
    private int entry_view_id;

    public JSONListAdapter (Activity activity, int entry_view_id, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.entry_view_id = entry_view_id;
        this.jsonArray = jsonArray;
        this.activity = activity;
    }


    @Override public int getCount() {
        if(null==jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(null==jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(entry_view_id, null);
        }

        TextView text = (TextView)convertView.findViewById(R.id.full_name);

        JSONObject json_data = getItem(position);
        if(null!=json_data){
            try {
                text.setText(this.jsonArray.getJSONObject(position).getString("firstName") +
                             " " +
                             this.jsonArray.getJSONObject(position).getString("lastName"));
            } catch (JSONException e) {
                Log.e("Exception", e.toString());
            }
        }

        return convertView;
    }
}
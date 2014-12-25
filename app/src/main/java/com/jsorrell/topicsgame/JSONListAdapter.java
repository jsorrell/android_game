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

abstract public class JSONListAdapter extends BaseAdapter implements ListAdapter {
    protected final Activity activity;
    protected final JSONArray jsonArray;
    protected int entry_view_id;

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
}
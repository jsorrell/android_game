package com.jsorrell.topicsgame;

import android.app.Application;
import android.content.Context;

/**
 * Created by jsorrell on 12/10/14.
 */
public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}

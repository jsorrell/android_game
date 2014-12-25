package com.jsorrell.topicsgame;

import com.jsorrell.topicsgame.HttpRequest.AsyncHttpRequest;
import com.jsorrell.topicsgame.HttpRequest.HttpRequest;
import com.loopj.android.http.*;

import java.util.ArrayList;

/**
 * Created by jsorrell on 12/7/14.
 */

public class RestClient {
    static final String baseUrl = App.getContext().getString(R.string.rest_url);
    static final int port = 8080;
    static final AsyncHttpRequest asyncRequest = new AsyncHttpRequest(port);
    static final HttpRequest syncRequest = new HttpRequest(port);


    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }

    public static void sendLoginRequestAsync(String mEmail, String mPassword, JsonHttpResponseHandler responseHandler) {
        // TODO: attempt authentication against a network service.
        RequestParams p = new RequestParams();
        p.put("password", mPassword);
        p.put("email", mEmail);
        //handler has to verify the login
        asyncRequest.get(getAbsoluteUrl("login"), p, responseHandler);
        // TODO: register the new account here.
    }

    public static void sendRegistrationIdSync(int userId, String regId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("regId", regId);
        syncRequest.post(getAbsoluteUrl("users/" + Integer.toString(userId) + "/reg/" + regId), params, responseHandler);
    }

    public static void getFriendListAsync(int userId, JsonHttpResponseHandler responseHandler) {
        asyncRequest.get(getAbsoluteUrl("user/" + userId + "/friends"), null, responseHandler);
    }

    public static void sendTopicCreationRequestAsync(int userId, String topic, ArrayList<Integer> userIds, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("gameTopic", topic);
        params.put("creator", userId);
        params.put("players", userIds);
        asyncRequest.post(getAbsoluteUrl("/users/" + Integer.toString(userId) + "/games"),
                params,
                responseHandler);
    }

    public static void getGamesListAsync(int userId, JsonHttpResponseHandler responseHandler) {
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/games"), null, responseHandler);
    }
}
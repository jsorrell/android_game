package com.jsorrell.topicsgame;

import android.util.Log;

import com.jsorrell.topicsgame.HttpRequest.AsyncHttpRequest;
import com.jsorrell.topicsgame.HttpRequest.HttpRequest;
import com.loopj.android.http.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jsorrell on 12/7/14.
 */

public class RestClient {
    static final String baseUrl = App.getContext().getString(R.string.rest_url);
    static final int port = App.getContext().getResources().getInteger(R.integer.rest_port);
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
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/friends"), null, responseHandler);
    }

    public static void getPendingFriendsAsync(int userId, JsonHttpResponseHandler responseHandler) {
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/friends/pending"),
                                        null,
                                        responseHandler);
    }

    public static void searchFriend(String name, JsonHttpResponseHandler responseHandler) {
        String[] nameParts = name.split("\\s+");
        if (nameParts.length < 2) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("firstName", nameParts[0]);
        params.put("lastName", nameParts[1]);
        asyncRequest.get(getAbsoluteUrl("users"), params, responseHandler);
    }

    public static void sendFriendRequest(int myId,
                                         int potential_friend_id,
                                         JsonHttpResponseHandler responseHandler)
    {
        RequestParams params = new RequestParams();
        params.put("status", 0);
        params.put("potential_friend", potential_friend_id);
        asyncRequest.post(getAbsoluteUrl("/users/" + Integer.toString(myId) + "/friends/pending"),
                                         params,
                                         responseHandler);
    }

    public static void acceptFriendRequest(int myId,
                                           int potential_friend_id,
                                           JsonHttpResponseHandler responseHandler)
    {
        RequestParams params = new RequestParams();
        params.put("status", 1);
        params.put("potential_friend", potential_friend_id);
        asyncRequest.post(getAbsoluteUrl("/users/" + Integer.toString(myId) + "/friends/pending"),
                          params,
                          responseHandler);
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

    public static void sendGamesAcceptedAsync(int userId, ArrayList<Integer> acceptedGames,
                                              JsonHttpResponseHandler responseHandler)
    {
        RequestParams params = new RequestParams();
        params.put("acceptedGames", acceptedGames);
        asyncRequest.post(getAbsoluteUrl("users/" + userId + "/games/pending"),
                          params,
                          responseHandler);
    }
    public static void getActiveGamesListAsync(int userId, JsonHttpResponseHandler responseHandler) {
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/games"), null, responseHandler);
    }

    public static void getPendingGamesListAsync(int userId, JsonHttpResponseHandler responseHandler) {
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/games/pending"), null, responseHandler);
    }

    public static void uploadSubmission(int userId,
                                        int gameId,
                                        JsonHttpResponseHandler responseHandler,
                                        InputStream data)
    {
        RequestParams params = new RequestParams();
        params.put("test", 1);
        params.put("data", data);
        asyncRequest.post(getAbsoluteUrl("users/" + userId + "/games" + gameId + "/submissions"),
                                          params,
                                          responseHandler);
    }

    public static void getSubmissions(int userId,
                                      int gameId,
                                      JsonHttpResponseHandler responseHandler)
    {
        Log.v("submission", Integer.toString(gameId));
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/games/" + gameId + "/submissions"),
                                        null,
                                        responseHandler);
    }

    public static void getSpecificSubmission(int userId,
                                             int submissionId,
                                             FileAsyncHttpResponseHandler responseHandler)
    {
        asyncRequest.get(getAbsoluteUrl("users/" + userId + "/submissions/" + submissionId),
                         null,
                         responseHandler);
    }
}

package com.jsorrell.topicsgame.HttpRequest;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jsorrell on 12/10/14.
 */
public class AsyncHttpRequest {

    private AsyncHttpClient client;

    public AsyncHttpRequest(int port) {
        client = new AsyncHttpClient(port);
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        this.client.get(url, params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        this.client.post(url, params, responseHandler);
    }

    public void delete(String url, AsyncHttpResponseHandler responseHandler) {
        this.client.delete(url, responseHandler);
    }
}

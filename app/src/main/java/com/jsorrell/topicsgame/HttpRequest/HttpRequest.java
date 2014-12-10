package com.jsorrell.topicsgame.HttpRequest;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by jsorrell on 12/10/14.
 */
public class HttpRequest {
    private SyncHttpClient client;

    public HttpRequest(int port) {
        client = new SyncHttpClient(port);
    }

    public void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        this.client.get(url, params, responseHandler);
    }

    public void post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        this.client.post(url, params, responseHandler);
    }

    public void delete(String url, ResponseHandlerInterface responseHandler) {
        this.client.delete(url, responseHandler);
    }

}

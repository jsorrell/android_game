package com.jsorrell.topicsgame;
import com.loopj.android.http.*;

/**
 * Created by jsorrell on 12/7/14.
 */

public class SyncRestClient {
    private static final String BASE_URL = "http://192.168.1.13/api/";

    private static AsyncHttpClient client = new AsyncHttpClient(8080);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
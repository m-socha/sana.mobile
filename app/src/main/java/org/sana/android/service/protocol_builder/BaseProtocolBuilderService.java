package org.sana.android.service.protocol_builder;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.sana.android.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class BaseProtocolBuilderService {

    private static final String SCHEME = "http";

    public interface Callback<T> {
        void onSuccess(T responseBody);
        void onFailure(HttpResponse httpResponse);
    }

    private class ProtocolBuilderHttpTask<T> extends AsyncTask<HttpUriRequest, Void, HttpResponse> {
        private Class<T> mType;
        private Callback<T> mCallback;

        public ProtocolBuilderHttpTask(Class<T> type, Callback<T> callback) {
            mType = type;
            mCallback = callback;
        }

        @Override
        protected HttpResponse doInBackground(HttpUriRequest... requests) {
            try {
                HttpUriRequest request = requests[0];
                HttpClient client = new DefaultHttpClient();//HttpClientFactory.getClient();
                HttpResponse response = client.execute(request);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            if (response != null && response.getStatusLine().getStatusCode() >= 200 &&
                    response.getStatusLine().getStatusCode() <= 299) {
                try {
                    String responseString = EntityUtils.toString(response.getEntity());
                    T responseBody = new Gson().fromJson(responseString, mType);
                    mCallback.onSuccess(responseBody);
                } catch (IOException e) {
                    e.printStackTrace();
                    mCallback.onFailure(response);
                }
            } else {
                mCallback.onFailure(response);
            }
        }
    }

    protected <T> void executeRequest(HttpUriRequest request, Class<T> type, Callback<T> callback) {
        ProtocolBuilderHttpTask<T> task = new ProtocolBuilderHttpTask(type, callback);
        task.execute(request);
    }

    protected final HttpPost getPostRequest(List<NameValuePair> postData) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(getUri());
        httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
        return httpPost;
    }

    protected final HttpGet getGetRequest() {
        return new HttpGet(getUri());
    }

    private final String getUri() {
        return SCHEME + "://" + Constants.DEFAULT_PROTOCOL_BUILDER_SERVER + getEndpoint();
    }

    protected abstract String getEndpoint();
}

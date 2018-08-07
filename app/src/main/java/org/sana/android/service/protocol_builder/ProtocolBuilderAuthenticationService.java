package org.sana.android.service.protocol_builder;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.sana.core.protocol_builder.AuthData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProtocolBuilderAuthenticationService extends BaseProtocolBuilderService {
    public interface Callback {
        void onSuccess(String authToken);
    }

    public void requestService(String username, String password, final Callback callback) {
        List<NameValuePair> postData = new ArrayList();
        postData.add(new BasicNameValuePair("username", username));
        postData.add(new BasicNameValuePair("password", password));

        try {
            HttpPost post = getPostRequest(postData);
            executeRequest(post, AuthData.class, new BaseProtocolBuilderService.Callback<AuthData>() {
                @Override
                public void onSuccess(AuthData responseBody) {
                    Log.d("success","big swang");
                    callback.onSuccess(responseBody.token);
                }

                @Override
                public void onFailure(HttpResponse httpResponse) {
                    Log.d("success", "big fail");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getEndpoint() {
        return "/auth/login";
    }
}

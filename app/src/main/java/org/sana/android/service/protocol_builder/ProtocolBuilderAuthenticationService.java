package org.sana.android.service.protocol_builder;

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
        void onFailure();
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
                    if (responseBody.success) {
                        callback.onSuccess(responseBody.token);
                    } else {
                        callback.onFailure();
                    }
                }

                @Override
                public void onFailure(HttpResponse httpResponse) {
                    callback.onFailure();
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

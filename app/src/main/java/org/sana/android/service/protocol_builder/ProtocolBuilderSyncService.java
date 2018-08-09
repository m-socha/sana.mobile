package org.sana.android.service.protocol_builder;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.sana.android.util.SanaUtil;

public class ProtocolBuilderSyncService extends BaseProtocolBuilderService {
    
    public void requestService(final Context context) {
        HttpGet get = getGetRequest();
        executeRequest(get, new TypeToken<String>() {
        }.getType(), new Callback<String>() {
            @Override
            public void onSuccess(String protocolContent) {
                SanaUtil.insertProcedure(context, protocolContent);
            }

            @Override
            public void onFailure(HttpResponse httpResponse) {

            }
        });
    }

    @Override
    protected String getEndpoint() {
        return "/api/procedures/7/generate";
    }
}

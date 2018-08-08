package org.sana.android.service.protocol_builder;

import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.sana.core.protocol_builder.Procedure;

import java.util.List;

public class ProtocolBuilderProcedureService extends BaseProtocolBuilderService {
    public interface Callback {
        void onSuccess(List<Procedure> procedures);
        void onFailure();
    }

    public void requestService(final Callback callback) {
        HttpGet get = getGetRequest();
        executeRequest(get, new TypeToken<List<Procedure>>() {
        }.getType(), new BaseProtocolBuilderService.Callback<List<Procedure>>() {
            @Override
            public void onSuccess(List<Procedure> procedures) {
                callback.onSuccess(procedures);
            }

            @Override
            public void onFailure(HttpResponse httpResponse) {
                callback.onFailure();
            }
        });
    }

    @Override
    protected String getEndpoint() {
        return "/api/procedures";
    }
}

package org.sana.android.service.protocol_builder;

public class ProtocolBuilderSessionService {

    private static ProtocolBuilderSessionService instance;

    public static ProtocolBuilderSessionService getInstance() {
        if (instance == null) {
            instance = new ProtocolBuilderSessionService();
        }

        return instance;
    }

    private String mSessionToken;

    public String getSessionToken() {
       return mSessionToken;
    }

    public boolean isSessionActive() {
        return (mSessionToken != null);
    }

    public void setSessionToken(String sessionToken) {
        mSessionToken = sessionToken;
    }

    public void clearSessionToken() {
        mSessionToken = null;
    }
}

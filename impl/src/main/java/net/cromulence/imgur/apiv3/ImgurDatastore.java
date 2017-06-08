package net.cromulence.imgur.apiv3;

import net.cromulence.datawrapper.DataStoreConnector;
import net.cromulence.datawrapper.DataWrapper;
import net.cromulence.datawrapper.DataWrapperImpl;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthData;

public class ImgurDatastore extends DataWrapperImpl implements ImgurOAuthData, ImgurApplicationData {

    private static final String KEY_CLIENT_ID = "key-client-id";
    private static final String KEY_CLIENT_SECRET = "key-client-secret";

    private static final String KEY_ACCESS_TOKEN = "key-access-token";
    private static final String KEY_ACCESS_TOKEN_TIMEOUT = "key-access-token-timeout";
    private static final String KEY_REFRESH_TOKEN = "key-refresh-token";
    private static final String KEY_USERNAME = "key-username";

    public ImgurDatastore(DataStoreConnector connector) {
        super(connector);
    }

    public ImgurDatastore(DataStoreConnector connector, String prefix) {
        super(connector, prefix);
    }

    public ImgurDatastore(DataWrapper delegateWrapper) {
        super(delegateWrapper);
    }

    public ImgurDatastore(DataWrapper delegateWrapper, String prefix) {
        super(delegateWrapper, prefix);
    }

    @Override
    public void setClientId(String clientId) {
        putString(KEY_CLIENT_ID, clientId);
    }

    @Override
    public String getClientId() {
        return getString(KEY_CLIENT_ID);
    }

    @Override
    public void setClientSecret(String clientSecret) {
        putString(KEY_CLIENT_SECRET, clientSecret);
    }

    @Override
    public String getClientSecret() {
        return getString(KEY_CLIENT_SECRET);
    }

    @Override
    public void setAccessToken(String token) {
        putString(KEY_ACCESS_TOKEN, token);
    }

    @Override
    public String getAccessToken() {
        return getString(KEY_ACCESS_TOKEN);
    }

    @Override
    public void setAccessTokenTimeout(long time) {
        putLong(KEY_ACCESS_TOKEN_TIMEOUT, time);
    }

    @Override
    public long getAccessTokenTimeout() {
        return getOrInsertLong(KEY_ACCESS_TOKEN_TIMEOUT, -1L);
    }

    @Override
    public void setRefreshToken(String token) {
        putString(KEY_REFRESH_TOKEN, token);
    }

    @Override
    public String getRefreshToken() {
        return getString(KEY_REFRESH_TOKEN);
    }

    @Override
    public void setUsername(String username) {
        putString(KEY_USERNAME, username);
    }

    @Override
    public String getUsername() {
        return getString(KEY_USERNAME);
    }
}

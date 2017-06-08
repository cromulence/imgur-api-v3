package net.cromulence.imgur.apiv3.auth;

import net.cromulence.imgur.apiv3.datamodel.AuthResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistingOAuthHandler extends AbstractAuthHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PersistingOAuthHandler.class);

    private ImgurOAuthData data;

    public PersistingOAuthHandler(ImgurOAuthData data) {
        this.data = data;
    }

    @Override
    public void handleAuth(AuthResponse ar) {
        data.setUsername(ar.getAccount_username());
        data.setAccessToken(ar.getAccess_token());
        data.setRefreshToken(ar.getRefresh_token());
        long now = System.currentTimeMillis();
        long accessTokenTimeout = now + (ar.getExpires_in() * 1000L);
        data.setAccessTokenTimeout(accessTokenTimeout);
        LOG.info("handleAuth: {} now {} timeout {}", data.getUsername(), now, accessTokenTimeout);
    }

    @Override
    public String getRefreshToken() {
        return data.getRefreshToken();
    }

    @Override
    public String getAccessToken() {
        return data.getAccessToken();
    }

    public long getAccessTokenTimeout() {
        return data.getAccessTokenTimeout();
    }

    @Override
    public void logout() {
        data.setUsername(null);
        data.setAccessToken(null);
        data.setAccessTokenTimeout(-1);
    }

}

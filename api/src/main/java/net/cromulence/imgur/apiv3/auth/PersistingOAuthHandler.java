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
        data.setUsername(ar.getAccountUsername());
        data.setAccessToken(ar.getAccessToken());
        data.setRefreshToken(ar.getRefreshToken());
        long now = System.currentTimeMillis();
        long accessTokenTimeout = now + (ar.getExpiresIn() * 1000L);
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

    @Override
    public long getAccessTokenTimeout() {
        return data.getAccessTokenTimeout();
    }

    @Override
    public String getUsername() {
        return data.getUsername();
    }

    @Override
    public void logout() {
        data.setUsername(null);
        data.setAccessToken(null);
        data.setAccessTokenTimeout(-1);
    }
}

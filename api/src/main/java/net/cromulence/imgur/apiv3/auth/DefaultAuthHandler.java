package net.cromulence.imgur.apiv3.auth;

import net.cromulence.imgur.apiv3.datamodel.AuthResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthHandler extends AbstractAuthHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthHandler.class);

    private String refreshToken;
    private String accessToken;
    private long accessTokenTimeout;

    @Override
    public void handleAuth(AuthResponse ar) {
        refreshToken = ar.getRefreshToken();
        accessToken = ar.getAccessToken();
        accessTokenTimeout = ar.getExpiresIn();

        final long now = System.currentTimeMillis();
        accessTokenTimeout = now + (ar.getExpiresIn() * 1000L);
        LOG.info("handleAuth: now {} timeout {}", now, accessTokenTimeout);
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public long getAccessTokenTimeout() {
        return accessTokenTimeout;
    }

    @Override
    public void logout() {
        accessTokenTimeout = -1;
        accessToken = null;
        refreshToken = null;
    }

}

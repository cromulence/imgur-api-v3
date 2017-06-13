package net.cromulence.imgur.apiv3.auth;

public abstract class AbstractAuthHandler implements ImgurOAuthHandler {

    @Override
    public boolean hasToken() {
        final String accessToken = getAccessToken();

        return accessToken != null && accessToken.length() > 0 && (getAccessTokenTimeout() != -1);
    }

    @Override
    public boolean isAccessTokenExpired() {
        final long now = System.currentTimeMillis();
        final long timeout = getAccessTokenTimeout();
        return now > timeout;
    }

    @Override
    public boolean hasValidAccessToken() {
        return hasToken() && !isAccessTokenExpired();
    }
}
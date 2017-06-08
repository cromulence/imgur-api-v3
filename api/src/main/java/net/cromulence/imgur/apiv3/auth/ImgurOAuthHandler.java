package net.cromulence.imgur.apiv3.auth;

import net.cromulence.imgur.apiv3.datamodel.AuthResponse;

public interface ImgurOAuthHandler {
    void handleAuth(AuthResponse ar);

    String getRefreshToken();

    String getAccessToken();

    boolean hasToken();

    long getAccessTokenTimeout();

    boolean isAccessTokenExpired();

    void logout();

    boolean hasValidAccessToken();
}

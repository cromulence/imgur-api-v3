package net.cromulence.imgur.apiv3.auth;

public interface ImgurOAuthData {

    void setAccessToken(String token);

    String getAccessToken();

    void setAccessTokenTimeout(long time);

    long getAccessTokenTimeout();

    void setRefreshToken(String token);

    String getRefreshToken();

    void setUsername(String username);

    String getUsername();
}

package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.ImgurApplicationData;
import net.cromulence.imgur.apiv3.auth.AuthUtils;
import net.cromulence.imgur.apiv3.auth.DefaultAuthHandler;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.endpoints.AccountEndpoint;
import net.cromulence.imgur.apiv3.endpoints.AlbumEndpoint;
import net.cromulence.imgur.apiv3.endpoints.CommentEndpoint;
import net.cromulence.imgur.apiv3.endpoints.CreditsEndpoint;
import net.cromulence.imgur.apiv3.endpoints.GalleryEndpoint;
import net.cromulence.imgur.apiv3.endpoints.ImageEndpoint;
import net.cromulence.imgur.apiv3.endpoints.NotificationEndpoint;
import net.cromulence.imgur.apiv3.endpoints.TopicEndpoint;

import javax.net.ssl.SSLContext;

public class Imgur {

    public final ImgurApplicationData DATA;

    public final HttpUtils HTTP;

    public final ImgurOAuthHandler AUTH;
    public final AuthUtils AUTH_UTILS;

    public final AccountEndpoint ACCOUNT;
    public final CommentEndpoint COMMENT;
    public final CreditsEndpoint CREDITS;
    public final GalleryEndpoint GALLERY;
    public final ImageEndpoint IMAGE;
    public final NotificationEndpoint NOTIFICATION;
    public final TopicEndpoint TOPIC;
    public final AlbumEndpoint ALBUM;

    public Imgur(ImgurApplicationData data) {
        this(data, null, new DefaultAuthHandler());
    }

    public Imgur(ImgurApplicationData data, SSLContext ctx) {
        this(data, ctx, new DefaultAuthHandler());
    }

    public Imgur(ImgurApplicationData data, ImgurOAuthHandler auth) {
        this(data, null, auth);
    }

    public Imgur(ImgurApplicationData data, SSLContext ctx, ImgurOAuthHandler auth) {

        DATA = data;

        if (ctx == null) {
            HTTP = new HttpUtils(this);
        } else {
            HTTP = new HttpUtils(this, ctx);
        }

        AUTH = auth;
        AUTH_UTILS = new AuthUtils(this);

        ACCOUNT = new AccountEndpoint(this);
        ALBUM = new AlbumEndpoint(this);
        COMMENT = new CommentEndpoint(this);
        CREDITS = new CreditsEndpoint(this);
        GALLERY = new GalleryEndpoint(this);
        IMAGE = new ImageEndpoint(this);
        NOTIFICATION = new NotificationEndpoint(this);
        TOPIC = new TopicEndpoint(this);
    }
}

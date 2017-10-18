package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.ImgurApplicationData;
import net.cromulence.imgur.apiv3.endpoints.AuthEndpoint;
import net.cromulence.imgur.apiv3.auth.DefaultAuthHandler;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.endpoints.AccountEndpoint;
import net.cromulence.imgur.apiv3.endpoints.AlbumEndpoint;
import net.cromulence.imgur.apiv3.endpoints.CommentEndpoint;
import net.cromulence.imgur.apiv3.endpoints.CreditsEndpoint;
import net.cromulence.imgur.apiv3.endpoints.GalleryEndpoint;
import net.cromulence.imgur.apiv3.endpoints.ImageEndpoint;
import net.cromulence.imgur.apiv3.endpoints.NotificationEndpoint;
import net.cromulence.imgur.apiv3.endpoints.TagsEndpoint;
import net.cromulence.imgur.apiv3.endpoints.TopicEndpoint;

import javax.net.ssl.SSLContext;

public class Imgur {

    public final ImgurApplicationData data;

    public final HttpUtils http;

    public final ImgurOAuthHandler authHandler;

    public final AuthEndpoint auth;
    public final AccountEndpoint account;
    public final AlbumEndpoint album;
    public final CommentEndpoint comment;
    public final CreditsEndpoint credits;
    public final GalleryEndpoint gallery;
    public final ImageEndpoint image;
    public final NotificationEndpoint endpoint;
    public final TagsEndpoint tags;
    public final TopicEndpoint topic;

    /**
     * Simplest possible constructor
     * @param clientId Imgur application client id
     * @param clientSecret Imgur application client secret
     */
    public Imgur(String clientId, String clientSecret) {
        this(new ImgurApplicationData() {
            @Override
            public String getClientId() {
                return clientId;
            }

            @Override
            public void setClientId(String clientId) {
                // no op
            }

            @Override
            public String getClientSecret() {
                return clientSecret;
            }

            @Override
            public void setClientSecret(String clientSecret) {
                // no op
            }
        }, null, new DefaultAuthHandler());
    }

    public Imgur(ImgurApplicationData data) {
        this(data, null, new DefaultAuthHandler());
    }

    public Imgur(ImgurApplicationData data, SSLContext ctx) {
        this(data, ctx, new DefaultAuthHandler());
    }

    public Imgur(ImgurApplicationData data, ImgurOAuthHandler auth) {
        this(data, null, auth);
    }

    public Imgur(ImgurApplicationData data, SSLContext ctx, ImgurOAuthHandler authHandler) {
        this.data = data;

        if (ctx == null) {
            http = new HttpUtils(this);
        } else {
            http = new HttpUtils(this, ctx);
        }

        this.authHandler = authHandler;

        auth = new AuthEndpoint(this);
        account = new AccountEndpoint(this);
        album = new AlbumEndpoint(this);
        comment = new CommentEndpoint(this);
        credits = new CreditsEndpoint(this);
        gallery = new GalleryEndpoint(this);
        image = new ImageEndpoint(this);
        endpoint = new NotificationEndpoint(this);
        tags = new TagsEndpoint(this);
        topic = new TopicEndpoint(this);
    }
}

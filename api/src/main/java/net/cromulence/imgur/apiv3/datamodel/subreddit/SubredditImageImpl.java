package net.cromulence.imgur.apiv3.datamodel.subreddit;

import net.cromulence.imgur.apiv3.datamodel.GalleryDetails;
import net.cromulence.imgur.apiv3.datamodel.GalleryImageImpl;
import net.cromulence.imgur.apiv3.datamodel.Image;

public class SubredditImageImpl extends GalleryImageImpl implements SubredditImage {

    private final String redditCommentsUrl;

    // TODO deserializer
    public SubredditImageImpl(Image image, GalleryDetails details, String redditCommentsUrl) {
        super(image, details);
        this.redditCommentsUrl = redditCommentsUrl;
    }

    public String getRedditCommentsUrl() {
        return redditCommentsUrl;
    }
}

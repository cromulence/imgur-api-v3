package net.cromulence.imgur.apiv3.datamodel.subreddit;

import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.GalleryAlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.GalleryDetails;

public class SubredditAlbumImpl extends GalleryAlbumImpl implements SubredditAlbum {

    private final String redditCommentsUrl;

    public SubredditAlbumImpl(Album album, GalleryDetails details, String redditCommentsUrl) {
        super(album, details);
        this.redditCommentsUrl = redditCommentsUrl;
    }

    public String getRedditCommentsUrl() {
        return redditCommentsUrl;
    }


}

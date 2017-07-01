package net.cromulence.imgur.apiv3.datamodel.subreddit;

import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.Uploaded;

public interface SubredditEntry<T extends Uploaded> extends GalleryEntry<T> {
    String getRedditCommentsUrl();
}

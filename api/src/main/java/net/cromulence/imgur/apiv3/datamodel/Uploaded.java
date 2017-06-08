package net.cromulence.imgur.apiv3.datamodel;

public interface Uploaded {

    int getDatetime();

    // optional
    String getDeleteHash();

    String getDescription();

    boolean isFavorite();

    String getId();

    boolean isInGallery();

    String getLink();

    boolean isNsfw();

    String getSection();

    String getTitle();

    int getViews();
}

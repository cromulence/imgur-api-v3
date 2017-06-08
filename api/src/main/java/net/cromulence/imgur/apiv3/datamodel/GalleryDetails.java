package net.cromulence.imgur.apiv3.datamodel;

public interface GalleryDetails {

    Long getAccountId();

    String getAccountUrl();

    int getCommentCount();

    int getDowns();

    boolean isAlbum();

    int getPoints();

    int getScore();

    String getTopic();

    int getTopicId();

    int getUps();

    String getVote();
}

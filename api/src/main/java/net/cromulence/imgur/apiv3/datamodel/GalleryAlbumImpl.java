package net.cromulence.imgur.apiv3.datamodel;

import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;

public class GalleryAlbumImpl implements Album, GalleryDetails, GalleryAlbum {

    private final Album album;

    private final GalleryDetails details;
    private int points;
    private String vote;

    public GalleryAlbumImpl(Album album, GalleryDetails details) {
        this.album = album;
        this.details = details;
    }

    @Override
    public Album get() {
        return album;
    }

    @Override
    public Long getAccountId() {
        return album.getAccountId();
    }

    @Override
    public String getAccountUrl() {
        return album.getAccountUrl();
    }

    @Override
    public String getLink() {
        return album.getLink();
    }

    @Override
    public boolean isNsfw() {
        return album.isNsfw();
    }

    @Override
    public String getDescription() {
        return album.getDescription();
    }

    @Override
    public boolean isFavorite() {
        return album.isFavorite();
    }

    @Override
    public String getTitle() {
        return album.getTitle();
    }

    @Override
    public int getViews() {
        return album.getViews();
    }

    @Override
    public int getDatetime() {
        return album.getDatetime();
    }

    @Override
    public String getId() {
        return album.getId();
    }

    @Override
    public String getDeleteHash() {
        return album.getDeleteHash();
    }

    @Override
    public int getOrder() {
        return album.getOrder();
    }

    @Override
    public String getSection() {
        return album.getSection();
    }

    @Override
    public String getCover() {
        return album.getCover();
    }

    @Override
    public int getCoverHeight() {
        return album.getCoverHeight();
    }

    @Override
    public int getCoverWidth() {
        return album.getCoverWidth();
    }

    @Override
    public Image[] getImages() {
        return album.getImages();
    }

    @Override
    public int getImagesCount() {
        return album.getImagesCount();
    }

    @Override
    public AlbumLayout getLayout() {
        return album.getLayout();
    }

    @Override
    public String getPrivacy() {
        return album.getPrivacy();
    }

    @Override
    public int getCommentCount() {
        return details.getCommentCount();
    }

    @Override
    public int getDowns() {
        return details.getDowns();
    }

    @Override
    public boolean isAlbum() {
        return details.isAlbum();
    }

    @Override
    public int getScore() {
        return details.getScore();
    }

    @Override
    public String getTopic() {
        return details.getTopic();
    }

    @Override
    public int getTopicId() {
        return details.getTopicId();
    }

    @Override
    public int getUps() {
        return details.getUps();
    }

    @Override
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public boolean isInGallery() {
        return album.isInGallery();
    }
}

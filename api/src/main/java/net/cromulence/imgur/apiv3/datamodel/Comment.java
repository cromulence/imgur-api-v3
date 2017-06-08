package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable, Notifiable {
    private long id;

    @SerializedName("image_id")
    private String imageId;

    private String comment;

    private String author;

    @SerializedName("author_id")
    private long authorId;

    @SerializedName("on_album")
    private boolean onAlbum;

    @SerializedName("album_cover")
    private String albumCover;

    private int ups;

    private int downs;

    private float points;

    private long datetime;

    @SerializedName("parent_id")
    private long parentId;

    private boolean deleted;

    private String vote;

    private Comment[] children;

    public String getId() {
        return Long.toString(id);
    }

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public boolean isOnAlbum() {
        return onAlbum;
    }

    public void setOnAlbum(boolean onAlbum) {
        this.onAlbum = onAlbum;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getParentId() {
        return Long.toString(parentId);
    }

    public void setParentId(String parentId) {
        this.parentId = Long.parseLong(parentId);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Comment[] getChildren() {
        return children;
    }

    public void setChildren(Comment[] children) {
        this.children = children;
    }

}

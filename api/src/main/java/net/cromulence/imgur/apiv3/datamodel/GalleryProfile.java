package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class GalleryProfile {

    // Total number of comments the user has made in the gallery
    @SerializedName("total_gallery_comments")
    private int totalGalleryComments;

    // Total number of items favorited by the user in the gallery
    @SerializedName("total_gallery_favorites")
    private int totalGalleryFavorites;

    //Total number of images submitted by the user.
    @SerializedName("total_gallery_submissions")
    private int totalGallerySubmissions;

    // An array of trophies that the user has.
    private Trophy[] trophies;

    public int getTotalGalleryComments() {
        return totalGalleryComments;
    }

    public void setTotalGalleryComments(int totalGalleryComments) {
        this.totalGalleryComments = totalGalleryComments;
    }

    public int getTotalGalleryFavorites() {
        return totalGalleryFavorites;
    }

    public void setTotalGalleryFavorites(int totalGalleryFavorites) {
        this.totalGalleryFavorites = totalGalleryFavorites;
    }

    public int getTotalGallerySubmissions() {
        return totalGallerySubmissions;
    }

    public void setTotalGallerySubmissions(int totalGallerySubmissions) {
        this.totalGallerySubmissions = totalGallerySubmissions;
    }

    public Trophy[] getTrophies() {
        return trophies;
    }

    public void setTrophies(Trophy[] trophies) {
        this.trophies = trophies;
    }
}

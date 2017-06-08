package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class Tag {

    private String name;// 	string 	Name of the tag.
    private int followers;// 	integer 	Number of followers for the tag.
    @SerializedName("total_items")
    private int totalItems; //integer 	Total number of gallery items for the tag.
    private boolean following;// 	boolean 	OPTIONAL, boolean representing whether or not the user is following the tag in their custom gallery
    private GalleryEntry items;// 	Array of Gallery Images and Gallery Albums 	Gallery items with current tag.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public GalleryEntry getItems() {
        return items;
    }

    public void setItems(GalleryEntry items) {
        this.items = items;
    }
}

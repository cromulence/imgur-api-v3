package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class CustomGallery {
    @SerializedName("account_url")
    private String accountUrl; // 	Username of the account that created the custom gallery
    private String link;// 	string 	The URL link to the custom gallery
    private String[] tags;// 	array 	An array of all the tag names in the custom gallery
    @SerializedName("item_count")
    private int itemCount; //integer 	The total number of gallery items in the custom gallery
    private GalleryEntry[] items;// 	Array of Gallery Images and Gallery Albums 	An array of all the gallery items in the custom gallery

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        accountUrl = accountUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public GalleryEntry[] getItems() {
        return items;
    }

    public void setItems(GalleryEntry[] items) {
        this.items = items;
    }
}

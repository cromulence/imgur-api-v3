package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class TagInfo {

    // "name": "the_more_you_know",
    private String name;

    //"display_name": "the more you know",
    @SerializedName("display_name")
    private String displayName;

    // "followers": 76,
    private int followers;

    // "total_items": 369594,
    @SerializedName("total_items")
    private int totalItems;

    // "following": false,
    private boolean following;

    // "background_hash": "EpmW3Oy",
    @SerializedName("background_hash")
    private String backgroundHash;

    // "is_promoted": false,
    @SerializedName("is_promoted")
    private boolean isPromoted;

    // "description": "",
    private String description;

    // "logo_hash": null,
    @SerializedName("logo_hash")
    private String logoHash;

    // "logo_destination_url": null,
    @SerializedName("logo_destination_url")
    private String destinationUrl;

    // "description_annotations": {}
    @SerializedName("description_annotations")
    private Object descriptionAnnotations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getBackgroundHash() {
        return backgroundHash;
    }

    public void setBackgroundHash(String backgroundHash) {
        this.backgroundHash = backgroundHash;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoHash() {
        return logoHash;
    }

    public void setLogoHash(String logoHash) {
        this.logoHash = logoHash;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public Object getDescriptionAnnotations() {
        return descriptionAnnotations;
    }

    public void setDescriptionAnnotations(Object descriptionAnnotations) {
        this.descriptionAnnotations = descriptionAnnotations;
    }
}

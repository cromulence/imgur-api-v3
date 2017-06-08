package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class ImageImpl implements Image {

    private int datetime;//    integer     Time inserted into the gallery, epoch time
    private String description;//     string  The description of the album in the gallery
    private boolean favorite;//    boolean     Indicates if the current user favorited the album. Defaults to false if not signed in.
    private String id;//  string  The ID for the album

    @SerializedName("in_gallery")
    private boolean inGallery;

    private String link;//    string  The URL link to the album
    private boolean nsfw;//    boolean     Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
    private String title;//   string  The title of the album in the gallery
    private int views;//   integer     The number of album views

    // common image fields

    private boolean animated;
    private long bandwidth;

    private String gifv;
    private int height;
    private boolean looping;
    private String mp4;
    private int size;
    private String type;
    private String vote;
    private int width;

    // Image fields
    private String name;

    @SerializedName("deletehash")
    private String deleteHash;//  string  OPTIONAL, the deletehash, if you're logged in as the album owner
    private String section;//      string  If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)
    private int mp4Size;

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    @Override
    public long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }

    @Override
    public String getGifv() {
        return gifv;
    }

    public void setGifv(String gifv) {
        this.gifv = gifv;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    @Override
    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    @Override
    public int getMp4Size() {
        return mp4Size;
    }

    public void setMp4Size(int mp4Size) {
        this.mp4Size = mp4Size;
    }

    @Override
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean isInGallery() {
        return inGallery;
    }

    public void setInGallery(boolean inGallery) {
        this.inGallery = inGallery;
    }
}

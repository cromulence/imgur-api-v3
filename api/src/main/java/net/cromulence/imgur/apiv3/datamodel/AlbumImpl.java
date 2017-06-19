package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;

public class AlbumImpl implements Album {

    // common
    @SerializedName("account_id")
    private Long accountId;//  int     The account ID or null if it's anonymous.

    @SerializedName("account_url")
    private String accountUrl;//    string  The account username or null if it's anonymous.
    private String cover;//   string  The ID of the album cover image

    @SerializedName("cover_height")
    private int coverHeight;//    integer     The height, in pixels, of the album cover image

    @SerializedName("cover_width")
    private int coverWidth;//     integer     The width, in pixels, of the album cover image
    private int datetime;//    integer     Time inserted into the gallery, epoch time
    private String description;//     string  The description of the album in the gallery
    private boolean favorite;//    boolean     Indicates if the current user favorited the album. Defaults to false if not signed in.
    private String id;//  string  The ID for the album
    private Image[] images;//  Array of Images     An array of all the images in the album (only available when requesting the direct album)

    @SerializedName("images_count")
    private int imagesCount;//    integer     The total number of images in the album
    private AlbumLayout layout;//  string  The view layout of the album.
    private String link;//    string  The URL link to the album
    private boolean nsfw;//    boolean     Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
    private String privacy;//     string  The privacy level of the album, you can only view public if not logged in as album owner
    private String title;//   string  The title of the album in the gallery
    private int views;//   integer     The number of album views

    @SerializedName("deletehash")
    private String deleteHash;//  string  OPTIONAL, the deletehash, if you're logged in as the album owner
    private int order;   // integer     Order number of the album on the user's album page (defaults to 0 if their albums haven't been reordered)
    private String section;//      string  If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)

    @SerializedName("in_gallery")
    private boolean inGallery;

    @Override
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

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
    public boolean isInGallery() {
        return inGallery;
    }

    public void setInGallery(boolean inGallery) {
        this.inGallery = inGallery;
    }

    @Override
    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(int coverHeight) {
        this.coverHeight = coverHeight;
    }

    @Override
    public int getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(int coverWidth) {
        this.coverWidth = coverWidth;
    }

    @Override
    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    @Override
    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    @Override
    public AlbumLayout getLayout() {
        return layout;
    }

    public void setLayout(AlbumLayout layout) {
        this.layout = layout;
    }

    @Override
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}

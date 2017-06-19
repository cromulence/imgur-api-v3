package net.cromulence.imgur.apiv3.datamodel;

public class GalleryImageImpl implements Image, GalleryDetails, GalleryImage {

    private final Image image;

    private final GalleryDetails details;
    private int points;

//    @SerializedName("account_id")
//    private Long accountId;//  int     The account ID or null if it's anonymous.

//    @SerializedName("account_url")
//    private String accountUrl ;//    string  The account username or null if it's anonymous.

    public GalleryImageImpl(Image image, GalleryDetails details) {
        this.image = image;
        this.details = details;
    }

    @Override
    public Image get() {
        return image;
    }

    public Image getImage() {
        return image;
    }

    public GalleryDetails getDetails() {
        return details;
    }

    //    public void setImage(Image image) {
//        this.image = image;
//    }

    @Override
    public String getName() {
        return image.getName();
    }

    @Override
    public boolean isAnimated() {
        return image.isAnimated();
    }

    @Override
    public long getBandwidth() {
        return image.getBandwidth();
    }

    @Override
    public String getDeleteHash() {
        return image.getDeleteHash();
    }

    @Override
    public String getGifv() {
        return image.getGifv();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public boolean isLooping() {
        return image.isLooping();
    }

    @Override
    public String getMp4() {
        return image.getMp4();
    }

    @Override
    public int getMp4Size() {
        return image.getMp4Size();
    }

    @Override
    public String getSection() {
        return image.getSection();
    }

    @Override
    public int getSize() {
        return image.getSize();
    }

    @Override
    public String getType() {
        return image.getType();
    }

    @Override
    public String getVote() {
        return image.getVote();
    }


    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public Long getAccountId() {
        return details.getAccountId();
    }

    @Override
    public String getAccountUrl() {
        return details.getAccountUrl();
    }

    @Override
    public String getLink() {
        return image.getLink();
    }

    @Override
    public boolean isNsfw() {
        return image.isNsfw();
    }

    @Override
    public boolean isInGallery() {
        return image.isInGallery();
    }

    @Override
    public String getDescription() {
        return image.getDescription();
    }

    @Override
    public boolean isFavorite() {
        return image.isFavorite();
    }

    @Override
    public String getTitle() {
        return image.getTitle();
    }

    @Override
    public int getViews() {
        return image.getViews();
    }

    @Override
    public int getDatetime() {
        return image.getDatetime();
    }

    @Override
    public String getId() {
        return image.getId();
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
}

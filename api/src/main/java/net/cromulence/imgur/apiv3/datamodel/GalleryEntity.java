package net.cromulence.imgur.apiv3.datamodel;

/** @deprecated */
public class GalleryEntity {
}

//implements Album, GalleryAlbum, Image, GalleryImage, Serializable {
//
//	// common
//	private int account_id;//  int     The account ID or null if it's anonymous.
//	private String account_url ;//    string  The account username or null if it's anonymous.
//	private String cover;//   string  The ID of the album cover image
//	private int cover_height;//    integer     The height, in pixels, of the album cover image
//	private int cover_width;//     integer     The width, in pixels, of the album cover image
//	private int datetime;//    integer     Time inserted into the gallery, epoch time
//	private String description;//     string  The description of the album in the gallery
//	private boolean favorite;//    boolean     Indicates if the current user favorited the album. Defaults to false if not signed in.
//	private String id;//  string  The ID for the album
//	private Image[] images;//  Array of Images     An array of all the images in the album (only available when requesting the direct album)
//	private int images_count;//    integer     The total number of images in the album
//	private String layout;//  string  The view layout of the album.
//	private String link;//    string  The URL link to the album
//	private boolean nsfw;//    boolean     Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
//	private String privacy;//     string  The privacy level of the album, you can only view public if not logged in as album owner
//	private String title;//   string  The title of the album in the gallery
//	private int views;//   integer     The number of album views
//
//	// album
//	private String deletehash;//  string  OPTIONAL, the deletehash, if you're logged in as the album owner
//	private int order;   // integer     Order number of the album on the user's album page (defaults to 0 if their albums haven't been reordered)
//	private String section;//      string  If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)
//
//	// Gallery entry
//	private int comment_count;
//	private int downs;
//	private boolean is_album;
//	private Integer score;
//	private String topic;
//	private int topic_id;
//	private int ups;
//
//    public int getAccountId() {
//		return account_id;
//	}
//
//	public void setAccountId(int account_id) {
//		this.account_id = account_id;
//	}
//
//	public String getAccountUrl() {
//		return account_url;
//	}
//
//	public void setAccountUrl(String account_url) {
//		this.account_url = account_url;
//	}
//
//	public String getCover() {
//		return cover;
//	}
//
//	public void setCover(String cover) {
//		this.cover = cover;
//	}
//
//	public int getCoverHeight() {
//		return cover_height;
//	}
//
//	public void setCoverHeight(int cover_height) {
//		this.cover_height = cover_height;
//	}
//
//	public int getCoverWidth() {
//		return cover_width;
//	}
//
//	public void setCoverWidth(int cover_width) {
//		this.cover_width = cover_width;
//	}
//
//	public int getDatetime() {
//		return datetime;
//	}
//
//	public void setDatetime(int datetime) {
//		this.datetime = datetime;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public boolean isFavorite() {
//		return favorite;
//	}
//
//	public void setFavorite(boolean favorite) {
//		this.favorite = favorite;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public Image[] getImages() {
//		return images;
//	}
//
//	public void setImages(Image[] images) {
//		this.images = images;
//	}
//
//	public int getImagesCount() {
//		return images_count;
//	}
//
//	public void setImagesCount(int images_count) {
//		this.images_count = images_count;
//	}
//
//	public String getLayout() {
//		return layout;
//	}
//
//	public void setLayout(String layout) {
//		this.layout = layout;
//	}
//
//	public String getLink() {
//		return link;
//	}
//	public void setLink(String link) {
//		this.link = link;
//	}
//
//	public boolean isNsfw() {
//		return nsfw;
//	}
//
//	public void setNsfw(boolean nsfw) {
//		this.nsfw = nsfw;
//	}
//
//	public String getPrivacy() {
//		return privacy;
//	}
//
//	public void setPrivacy(String privacy) {
//		this.privacy = privacy;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public int getViews() {
//		return views;
//	}
//
//	public void setViews(int views) {
//		this.views = views;
//	}
//
//	public String getDeletehash() {
//		return deletehash;
//	}
//
//	public void setDeletehash(String deletehash) {
//		this.deletehash = deletehash;
//	}
//
//	public int getOrder() {
//		return order;
//	}
//
//	public void setOrder(int order) {
//		this.order = order;
//	}
//
//	public String getSection() {
//		return section;
//	}
//
//	public void setSection(String section) {
//		this.section = section;
//	}
//
//	public int getCommentCount() {
//		return comment_count;
//	}
//
//	public void setCommentCount(int comment_count) {
//		this.comment_count = comment_count;
//	}
//
//	public int getDowns() {
//		return downs;
//	}
//
//	public void setDowns(int downs) {
//		this.downs = downs;
//	}
//
//	public boolean isAlbum() {
//		return is_album;
//	}
//
//	public void setAlbum(boolean is_album) {
//		this.is_album = is_album;
//	}
//
//	public Integer getScore() {
//		return score;
//	}
//
//	public void setScore(Integer score) {
//		this.score = score;
//	}
//
//	public String getTopic() {
//		return topic;
//	}
//
//	public void setTopic(String topic) {
//		this.topic = topic;
//	}
//
//	public int getTopicId() {
//		return topic_id;
//	}
//
//	public void setTopicId(int topic_id) {
//		this.topic_id = topic_id;
//	}
//
//	public int getUps() {
//		return ups;
//	}
//
//	public void setUps(int ups) {
//		this.ups = ups;
//	}
//
//
//	// common image fields
//
//	private boolean animated;
//	private long bandwidth;
//
//	private String gifv;
//	private int height;
//	private boolean looping;
//	private String mp4;
//	private int size;
//	private String type;
//	private String vote;
//	private String webm;
//	private int width;
//
//	// Image fields
//	private String name;
//
//	public boolean isAnimated() {
//		return animated;
//	}
//
//	public void setAnimated(boolean animated) {
//		this.animated = animated;
//	}
//
//	public long getBandwidth() {
//		return bandwidth;
//	}
//
//	public void setBandwidth(long bandwidth) {
//		this.bandwidth = bandwidth;
//	}
//
//	public String getGifv() {
//		return gifv;
//	}
//
//	public void setGifv(String gifv) {
//		this.gifv = gifv;
//	}
//
//	public int getHeight() {
//		return height;
//	}
//
//	public void setHeight(int height) {
//		this.height = height;
//	}
//
//	public boolean isLooping() {
//		return looping;
//	}
//
//	public void setLooping(boolean looping) {
//		this.looping = looping;
//	}
//
//	public String getMp4() {
//		return mp4;
//	}
//
//	public void setMp4(String mp4) {
//		this.mp4 = mp4;
//	}
//
//	public int getSize() {
//		return size;
//	}
//
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getVote() {
//		return vote;
//	}
//
//	public void setVote(String vote) {
//		this.vote = vote;
//	}
//
//	public String getWebm() {
//		return webm;
//	}
//
//	public void setWebm(String webm) {
//		this.webm = webm;
//	}
//
//	public int getWidth() {
//		return width;
//	}
//
//	public void setWidth(int width) {
//		this.width = width;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//}

package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.BasicResponse;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Account;
import net.cromulence.imgur.apiv3.datamodel.AccountSettings;
import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.GalleryProfile;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.datamodel.Notifications;
import net.cromulence.imgur.apiv3.datamodel.constants.AccountCommentSort;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumPrivacy;
import net.cromulence.imgur.apiv3.datamodel.constants.FavoriteSort;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AccountEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "account";
    private static final String ME = "me";

    private static final String SETTINGS = "settings";
    private static final String ALBUMS = "albums";
    private static final String COUNT = "count";
    private static final String COMMENTS = "comments";
    private static final String IMAGES = "images";

    public AccountEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Account getBaseInfo() throws ApiRequestException {
        return getBaseInfo(ME);
    }

    public Account getBaseInfo(String user) throws ApiRequestException {
        String infoUrl = endpointUrlWithSinglePathParameter(user);

        return getImgur().http.typedGet(infoUrl, Account.class, true);
    }

    public Paginated<GalleryEntry[]> getAccountGalleryFavourites() {
        return getAccountGalleryFavourites(ME, FavoriteSort.NEWEST);
    }

    public Paginated<GalleryEntry[]> getAccountGalleryFavourites(FavoriteSort sort) {
        return getAccountGalleryFavourites(ME, sort);
    }

    public Paginated<GalleryEntry[]> getAccountGalleryFavourites(String username) {
        return getAccountGalleryFavourites(username, FavoriteSort.NEWEST);
    }

    public Paginated<GalleryEntry[]> getAccountGalleryFavourites(String username, FavoriteSort sort)  {
        return new Paginated<>((int page) -> getAccountGalleryFavourites(username, page, sort));
    }

    public GalleryEntry[] getAccountGalleryFavourites(int page, FavoriteSort sort) throws ApiRequestException {
        return getAccountGalleryFavourites(ME, page, sort);
    }

    private GalleryEntry[] getAccountGalleryFavourites(String username, int page, FavoriteSort sort) throws ApiRequestException {
        return doGetFavourites(username, page, sort, "gallery_favorites");
    }

    public Paginated<GalleryEntry[]> getGalleryFavourites() {
        return getGalleryFavourites(ME, FavoriteSort.NEWEST);
    }

    public Paginated<GalleryEntry[]> getGalleryFavourites(FavoriteSort sort) {
        return getGalleryFavourites(ME, sort);
    }

    public Paginated<GalleryEntry[]> getGalleryFavourites(String username) {
        return getGalleryFavourites(username, FavoriteSort.NEWEST);
    }

    public Paginated<GalleryEntry[]> getGalleryFavourites(String username, FavoriteSort sort) {
        return new Paginated<>((int page) -> getGalleryFavourites(username, page, sort));
    }

    public GalleryEntry[] getGalleryFavourites(int page, FavoriteSort sort) throws ApiRequestException {
        return getGalleryFavourites(ME, page, sort);
    }

    private GalleryEntry[] getGalleryFavourites(String username, int page, FavoriteSort sort) throws ApiRequestException {
        return doGetFavourites(username, page, sort, "favorites");
    }

    private GalleryEntry[] doGetFavourites(String username, int page, FavoriteSort sort, String type) throws ApiRequestException {

        String favouritesUrl = endpointUrlWithMultiplePathParameters(username, type, page(page), sort.toString());

        return getImgur().http.typedGet(favouritesUrl, GalleryEntry[].class, true);
    }

    public Paginated<GalleryEntry[]> getAccountSubmissions() {
        return getAccountSubmissions(ME);
    }

    public Paginated<GalleryEntry[]> getAccountSubmissions(String username) {
        return new Paginated<>((int page) -> getAccountSubmissions(username, page));
    }

    public GalleryEntry[] getAccountSubmissions(String username, int page) throws ApiRequestException {
        String accountSubmissionUrl = endpointUrlWithMultiplePathParameters(username, "submissions", page(page));

        return getImgur().http.typedGet(accountSubmissionUrl, GalleryEntry[].class, true);
    }

    public AccountSettings getAccountSettings() throws ApiRequestException {
        String settingsUrl = endpointUrlWithMultiplePathParameters(ME, SETTINGS);

        return getImgur().http.typedGet(settingsUrl, AccountSettings.class);
    }

    public void updateBio(String bio) throws ApiRequestException {
        String settingsUrl = endpointUrlWithMultiplePathParameters(ME, SETTINGS);

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("bio", bio));

        getImgur().http.post(settingsUrl, params, true);
    }

    public void updateAccountSettings(AccountSettings settings) throws ApiRequestException {
        String settingsUrl = endpointUrlWithMultiplePathParameters(ME, SETTINGS);

        boolean publicImages = settings.isPublicImages();
        boolean messagingEnabled = settings.isMessagingEnabled();
        AlbumPrivacy albumPrivacy = settings.getAlbumPrivacy();
        boolean acceptedGalleryTerms = settings.isAcceptedGalleryTerms();
        String username = settings.getAccountUrl();
        boolean showMature = settings.isShowMature();
        boolean newsletterSubscribed = settings.isNewsletterSubscribed();

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("public_images", Boolean.toString(publicImages)));
        params.add(new BasicNameValuePair("messaging_enabled", Boolean.toString(messagingEnabled)));
        if (albumPrivacy != null) {
            params.add(new BasicNameValuePair("album_privacy", albumPrivacy.toString()));
        }
        params.add(new BasicNameValuePair("accepted_gallery_terms", Boolean.toString(acceptedGalleryTerms)));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("show_mature", Boolean.toString(showMature)));
        params.add(new BasicNameValuePair("newsletter_subscribed", Boolean.toString(newsletterSubscribed)));

        getImgur().http.post(settingsUrl, params, true);
    }

    public GalleryProfile getGalleryProfile() throws ApiRequestException {
        return getGalleryProfile(ME);
    }

    public GalleryProfile getGalleryProfile(String username) throws ApiRequestException {
        String galleryProfileUrl = endpointUrlWithMultiplePathParameters(username, "gallery_profile");

        return getImgur().http.typedGet(galleryProfileUrl, GalleryProfile.class);
    }

    public boolean hasVerifiedEmail() throws ApiRequestException {
        String verifyEmailUrl = endpointUrlWithMultiplePathParameters(ME, "verifyemail");

        return getImgur().http.typedGet(verifyEmailUrl, boolean.class);
    }

    /**
     * @return true if the email was requested, false otherwise. False could mean there was a HTTP error, or that the
     * user has already verified their email address.
     */
    public boolean requestVerificationEmail() throws ApiRequestException {
        String verifyEmailUrl = endpointUrlWithMultiplePathParameters(ME, "verifyemail");

        BasicResponse post = getImgur().http.post(verifyEmailUrl);

        return post.isSuccess();
    }

    public Album[] getAlbums() throws ApiRequestException {
        return getAlbums(ME, 0);
    }

    public Album[] getAlbums(String username) throws ApiRequestException {
        return getAlbums(username, 0);
    }

    public Album[] getAlbums(int page) throws ApiRequestException {
        return getAlbums(ME, page);
    }

    public Album[] getAlbums(String username, int page) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters(username, ALBUMS, Integer.toString(page));

        return getImgur().http.typedGet(albumUrl, Album[].class);
    }

    public Album getAlbum(String albumId) throws ApiRequestException {
        return getImgur().album.getAlbum(albumId);
    }

    public String[] getAlbumIds(int page) throws ApiRequestException {
        return getAlbumIds(ME, page);
    }

    public String[] getAlbumIds(String username, int page) throws ApiRequestException {
        String albumIdsUrl = endpointUrlWithMultiplePathParameters(username, ALBUMS, "ids", Integer.toString(page));

        return getImgur().http.typedGet(albumIdsUrl, String[].class);
    }

    public int getAlbumCount() throws ApiRequestException {
        return getAlbumCount(ME);
    }

    public int getAlbumCount(String username) throws ApiRequestException {
        String albumCountUrl = endpointUrlWithMultiplePathParameters(username, ALBUMS, COUNT);

        return getImgur().http.typedGet(albumCountUrl, Integer.class);
    }

    public void deleteAlbum(String albumId) throws ApiRequestException {
        getImgur().album.deleteAlbum(albumId);
    }

    public Paginated<Comment[]> getComments() {
        return getComments(ME, AccountCommentSort.NEWEST);
    }

    public Paginated<Comment[]> getComments(String username) {
        return getComments(username, AccountCommentSort.NEWEST);
    }

    public Paginated<Comment[]> getComments(AccountCommentSort sort) {
        return getComments(ME, sort);
    }

    public Paginated<Comment[]> getComments(String username, AccountCommentSort sort) {
        return new Paginated<>((int page) -> getComments(username, sort, page));
    }

    public Comment[] getComments(String username, AccountCommentSort sort, int page) throws ApiRequestException {
        String commentsUrl = endpointUrlWithMultiplePathParameters(username, COMMENTS, sort.toString(), page(page));

        return getImgur().http.typedGet(commentsUrl, Comment[].class);
    }

    public Comment getComment(String commentId) throws ApiRequestException {
        return getImgur().comment.getComment(commentId);
    }

    public Paginated<String[]> getCommentIds() {
        return getCommentIds(ME, AccountCommentSort.NEWEST);
    }

    public Paginated<String[]> getCommentIds(String username, AccountCommentSort sort) {
        return new Paginated<>((int page) -> getCommentIds(username, sort, page));
    }

    public String[] getCommentIds(String username, AccountCommentSort sort, int page) throws ApiRequestException {
        String commentIdsUrl = endpointUrlWithMultiplePathParameters(username, COMMENTS, "ids", sort.toString(), Integer.toString(page));

        return getImgur().http.typedGet(commentIdsUrl, String[].class);
    }

    public int getCommentCount() throws ApiRequestException {
        return getCommentCount(ME);
    }

    public int getCommentCount(String username) throws ApiRequestException {
        String commentCountUrl = endpointUrlWithMultiplePathParameters(username, COMMENTS, COUNT);

        return getImgur().http.typedGet(commentCountUrl, Integer.class);
    }

    public void deleteComment(String commentId) throws ApiRequestException {
        getImgur().comment.deleteComment(commentId);
    }

    public Paginated<Image[]> getImages() {
        return getImages(ME);
    }

    public Paginated<Image[]> getImages(String username) {
        return new Paginated<>((int page) -> getImages(username, page));
    }

    public Image[] getImages(String username, int page) throws ApiRequestException {
        String imagesUrl = endpointUrlWithMultiplePathParameters(username, IMAGES, page(page));

        return getImgur().http.typedGet(imagesUrl, Image[].class);
    }

    public Image getImage(String imageId) throws ApiRequestException {
        return getImgur().image.getImage(imageId);
    }

    public Paginated<String[]> getImageIds() {
        return getImageIds(ME);
    }

    public Paginated<String[]> getImageIds(String username) {
        return new Paginated<>((int page) -> getImageIds(username, page));
    }

    public String[] getImageIds(String username, int page) throws ApiRequestException {
        String imageIdsUrl = endpointUrlWithMultiplePathParameters(username, IMAGES, "ids", page(page));

        return getImgur().http.typedGet(imageIdsUrl, String[].class);
    }

    public int getImageCount() throws ApiRequestException {
        return getImageCount(ME);
    }

    public int getImageCount(String username) throws ApiRequestException {
        String imageCountUrl = endpointUrlWithMultiplePathParameters(username, IMAGES, COUNT);

        return getImgur().http.typedGet(imageCountUrl, Integer.class);
    }

    public void deleteImage(String imageId) throws ApiRequestException {
        getImgur().image.deleteImage(imageId);
    }


    public Notifications getReplies() throws ApiRequestException {
        return getReplies(true);
    }

    public Notifications getReplies(boolean newOnly) throws ApiRequestException {
        String repliesUrl = endpointUrlWithMultiplePathParameters(ME, "notifications", "replies");

        repliesUrl += "?new=" + newOnly;

        return getImgur().http.typedGet(repliesUrl, Notifications.class, true);
    }
}

package net.cromulence.imgur.apiv3.endpoints;

import static net.cromulence.imgur.apiv3.datamodel.constants.Section.HOT;

import net.cromulence.imgur.apiv3.Utils;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.CommentResponseData;
import net.cromulence.imgur.apiv3.datamodel.GalleryAlbum;
import net.cromulence.imgur.apiv3.datamodel.GalleryAlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.GalleryImage;
import net.cromulence.imgur.apiv3.datamodel.GalleryImageImpl;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.datamodel.Tag;
import net.cromulence.imgur.apiv3.datamodel.TagVote;
import net.cromulence.imgur.apiv3.datamodel.Vote;
import net.cromulence.imgur.apiv3.datamodel.constants.CommentSort;
import net.cromulence.imgur.apiv3.datamodel.constants.GalleryEntryType;
import net.cromulence.imgur.apiv3.datamodel.constants.GallerySort;
import net.cromulence.imgur.apiv3.datamodel.constants.GalleryWindow;
import net.cromulence.imgur.apiv3.datamodel.constants.ReportReason;
import net.cromulence.imgur.apiv3.datamodel.constants.Section;
import net.cromulence.imgur.apiv3.datamodel.constants.SubredditSort;
import net.cromulence.imgur.apiv3.datamodel.constants.TopicSort;
import net.cromulence.imgur.apiv3.datamodel.meme.MemeEntry;
import net.cromulence.imgur.apiv3.datamodel.meme.MemeImage;
import net.cromulence.imgur.apiv3.datamodel.meme.MemeImageImpl;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditAlbum;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditAlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditEntry;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditImage;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditImageImpl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GalleryEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "gallery";
    private static final String COMMENTS = "comments";
    private static final String COMMENT = "comment";

    public GalleryEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Paginated<GalleryEntry[]> gallery() {
        return gallery(HOT, GallerySort.VIRAL, GalleryWindow.DAY, true);
    }

    public Paginated<GalleryEntry[]> gallery(Section section, GallerySort sort, GalleryWindow window, boolean showViral) {
        return new Paginated<>((int page) -> gallery(section, sort, window, page, showViral));
    }

    public GalleryEntry[] gallery(Section section, GallerySort sort, GalleryWindow window, int page, boolean showViral) throws ApiRequestException {

        // https://api.imgur.com/3/gallery/{section}/{sort}/{page}?showViral=bool

        // section 	optional 	hot | top | user - defaults to hot
        // sort 	optional 	viral | top | time | rising (only available with user section) - defaults to viral
        // page 	optional 	integer - the data paging number
        // window 	optional 	Change the date range of the request if the section is "top", day | week | month | year | all, defaults to day
        // showViral 	optional 	true | false - Show or hide viral images from the 'user' section. Defaults to true

        String galleryUrl = endpointUrlWithMultiplePathParameters(section.toString(), sort.toString(), window.toString(), page(page));

        String urlFormat = "%s?showViral=%b";

        String url = String.format(urlFormat, galleryUrl, showViral);

        return getImgur().http.typedGet(url, GalleryEntry[].class, true);
    }

    public Paginated<MemeEntry[]> getMemes() {
        return getMemes(TopicSort.VIRAL, GalleryWindow.WEEK);
    }

    public Paginated<MemeEntry[]> getMemes(TopicSort sort, GalleryWindow window) {
        return new Paginated<>((int page) -> getMemes(sort, window, page));
    }

    public MemeEntry[] getMemes(TopicSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String memeUrl = baseUrlWithMultiplePathParameters("g", "memes", sort.toString(), window.toString(), page(page));

        return getImgur().http.typedGet(memeUrl, MemeEntry[].class);
    }

    public MemeImage getMemeImage(String memeId) throws ApiRequestException {
        String memeUrl = baseUrlWithMultiplePathParameters("g", "memes", memeId);

        return getImgur().http.typedGet(memeUrl, MemeImageImpl.class);
    }

    public Paginated<SubredditEntry[]> getSubredditGalleries(String subredditName) {
        return getSubredditGalleries(subredditName, SubredditSort.TIME, GalleryWindow.WEEK);
    }

    public Paginated<SubredditEntry[]> getSubredditGalleries(String subredditName, SubredditSort sort, GalleryWindow window) {
        return new Paginated<>((int page) -> getSubredditGalleries(subredditName, sort, window, page));
    }

    public SubredditEntry[] getSubredditGalleries(String subredditName, SubredditSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String subredditUrl = endpointUrlWithMultiplePathParameters("r", subredditName, sort.toString(), window.toString(), page(page));

        return getImgur().http.typedGet(subredditUrl, SubredditEntry[].class);
    }

    public SubredditImage getSubredditImage(String subredditName, String imageId) throws ApiRequestException {
        String subredditImageUrl = endpointUrlWithMultiplePathParameters("r", subredditName, imageId);

        return getImgur().http.typedGet(subredditImageUrl, SubredditImageImpl.class);
    }

    public SubredditAlbum getSubredditAlbum(String subredditName, String albumId) throws ApiRequestException {
        String subredditAlbumUrl = endpointUrlWithMultiplePathParameters("r", subredditName, albumId);

        SubredditEntry subredditAlbum = getImgur().http.typedGet(subredditAlbumUrl, SubredditAlbumImpl.class);

        return (SubredditAlbum) subredditAlbum;
    }

    public Paginated<Tag> getTagImages(String tagName) {
        return getTagImages(tagName, TopicSort.VIRAL, GalleryWindow.WEEK);
    }

    public Paginated<Tag> getTagImages(String tagName, TopicSort sort, GalleryWindow window) {
        return new Paginated<>(page -> getTagImages(tagName, sort, window, page));
    }

    public Tag getTagImages(String tagName, TopicSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(tagName, sort.toString(), window.toString(), page(page));

        return getImgur().http.typedGet(tagUrl, Tag.class);
    }

    public GalleryImage getTaggedImage(String tagName, String imageId) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(tagName, imageId);

        return getImgur().http.typedGet(tagUrl, GalleryImageImpl.class);
    }

    public TagVote[] getEntryTags(String entryId) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(entryId, "tags");

        return getImgur().http.typedGet(tagUrl, TagVote[].class);
    }

    public void voteOnTag(String entryId, String tagName, boolean upvote) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", "tag", tagName, upvote ? "up" : "down");

        getImgur().http.post(voteUrl);
    }

    public void addTags(String entryId, String[] tagNames) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters("tags", entryId);

        getImgur().http.post(voteUrl, getParamsFor("tags", Utils.asCommaSeparatedList(tagNames)), true);
    }

    public Paginated<GalleryEntry[]> search(String searchString) {
        return search(searchString, TopicSort.TIME, GalleryWindow.ALL);
    }

    public Paginated<GalleryEntry[]> search(String searchString, TopicSort sort, GalleryWindow window) {
        return new Paginated<>( page -> search(searchString, sort, window, page));
    }

    public GalleryEntry[] search(String searchString, TopicSort sort, GalleryWindow window, int page) throws ApiRequestException {
        // This is basic search only ('q' parameter). The client is responsible for constructing a valid search string
        String searchUrl = endpointUrlWithMultiplePathParameters("search", sort.toString(), window.toString(), page(page));
        searchUrl += "?q=" + searchString;

        return getImgur().http.typedGet(searchUrl, GalleryEntry[].class);
    }

    public Paginated<GalleryEntry[]> random() {
        return new Paginated<>(this::random);
    }

    public GalleryEntry[] random(int page) throws ApiRequestException {
        String randomUrl = endpointUrlWithMultiplePathParameters("random", "random", page(page));

        return getImgur().http.typedGet(randomUrl, GalleryEntry[].class);
    }

    public boolean shareImageWithCommunity(Image image) throws ApiRequestException {
        return shareWithCommunity(image.getId(), GalleryEntryType.IMAGE, image.getTitle(), image.isNsfw());
    }

    public boolean shareImageWithCommunity(String id, String title, boolean mature) throws ApiRequestException {
        return shareWithCommunity(id, GalleryEntryType.IMAGE, title, mature);
    }

    public boolean shareAlbumWithCommunity(Album album) throws ApiRequestException {
        return shareWithCommunity(album.getId(), GalleryEntryType.IMAGE, album.getTitle(), album.isNsfw());
    }

    public boolean shareAlbumWithCommunity(String id, String title, boolean mature) throws ApiRequestException {
        return shareWithCommunity(id, GalleryEntryType.ALBUM, title, mature);
    }

    private boolean shareWithCommunity(String id, GalleryEntryType type, String title, boolean mature) throws ApiRequestException {
        String submitUrl = getEndpointUrl() + "/" + type + "/" + id;

        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("terms", "1"));

        if (mature) {
            params.add(new BasicNameValuePair("mature", "1"));
        }

        return getImgur().http.typedPost(submitUrl, Boolean.class, params, true);
    }

    public void removeFromGallery(String id) throws ApiRequestException {
        String submitUrl = endpointUrlWithSinglePathParameter(id);

        getImgur().http.delete(submitUrl, true);
    }

    public GalleryAlbum getGalleryAlbum(String albumId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters("album", albumId);

        return getImgur().http.typedGet(albumUrl, GalleryAlbumImpl.class);
    }

    public GalleryImage getGalleryImage(String imageId) throws ApiRequestException {
        String imageUrl = endpointUrlWithMultiplePathParameters("image", imageId);

        return getImgur().http.typedGet(imageUrl, GalleryImageImpl.class);
    }

    public void reportGalleryEntry(String entryId) throws ApiRequestException {
        reportGalleryEntry(entryId, null);
    }

    public void reportGalleryEntry(String entryId, ReportReason reason) throws ApiRequestException {
        String reportUrl = endpointUrlWithMultiplePathParameters(entryId, "report");

        List<NameValuePair> params = new ArrayList<>();

        if (reason != null) {
            params.add(new BasicNameValuePair("reason", reason.toString()));
        }

        getImgur().http.post(reportUrl, params);
    }

    public Vote getGalleryEntryVotes(String entryId) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "votes");
        return getImgur().http.typedGet(voteUrl, Vote.class);
    }

    public void voteOnGalleryEntry(String entryId, boolean upvote) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", (upvote ? "up" : "down"));

        getImgur().http.post(voteUrl);
    }

    public void clearVoteOnGalleryEntry(String entryId) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", "veto");

        getImgur().http.post(voteUrl);
    }

    public Comment[] albumComments(String id, CommentSort sort) throws ApiRequestException {
        return comments(GalleryEntryType.ALBUM, id, sort);
    }

    public Comment[] imageComments(String id, CommentSort sort) throws ApiRequestException {
        return comments(GalleryEntryType.IMAGE, id, sort);
    }

    private Comment[] comments(GalleryEntryType type, String id, CommentSort sort) throws ApiRequestException {

        String commentsUrl = endpointUrlWithMultiplePathParameters(type.toString(), id, COMMENTS, sort.toString());

        return getImgur().http.typedGet(commentsUrl, Comment[].class);
    }

    public Comment getComment(String entryId, String commentId) throws ApiRequestException {
        String commentUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENT, commentId);

        return getImgur().http.typedGet(commentUrl, Comment.class);
    }

    public String addComment(String entryId, String comment) throws ApiRequestException {
        return replyToComment(entryId, null, comment);
    }

    public String replyToComment(String entryId, String parentCommentId, String comment) throws ApiRequestException {

        String commentUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENT, parentCommentId);

        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair(COMMENT, comment));

        CommentResponseData commentResponse = getImgur().http.typedPost(commentUrl, CommentResponseData.class, params, true);

        return Long.toString(commentResponse.getId());
    }

    public String[] getCommentIds(String entryId) throws ApiRequestException {
        String commentIdsUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENTS, "ids");

        return getImgur().http.typedGet(commentIdsUrl, String[].class);
    }

    public int getCommentCount(String entryId) throws ApiRequestException {
        String commentCountUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENTS, "count");

        return getImgur().http.typedGet(commentCountUrl, Integer.class);
    }
}

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

    public Paginated<GalleryEntry[]> gallery() throws ApiRequestException {
        return gallery(HOT, GallerySort.VIRAL, GalleryWindow.DAY, true);
    }

    public Paginated<GalleryEntry[]> gallery(Section section, GallerySort sort, GalleryWindow window, boolean showViral) throws ApiRequestException {
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

        return getImgur().HTTP.typedGet(url, GalleryEntry[].class, true);
    }

    public Paginated<MemeEntry[]> getMemes() throws ApiRequestException {
        return getMemes(TopicSort.VIRAL, GalleryWindow.WEEK);
    }

    public Paginated<MemeEntry[]> getMemes(TopicSort sort, GalleryWindow window) throws ApiRequestException {
        return new Paginated<>((int page) -> getMemes(sort, window, page));
    }

    public MemeEntry[] getMemes(TopicSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String memeUrl = baseUrlWithMultiplePathParameters("g", "memes", sort.toString(), window.toString(), page(page));

        return getImgur().HTTP.typedGet(memeUrl, MemeEntry[].class);
    }

    public MemeImage getMemeImage(String memeId) throws ApiRequestException {
        String memeUrl = baseUrlWithMultiplePathParameters("g", "memes", memeId);

        return getImgur().HTTP.typedGet(memeUrl, MemeImageImpl.class);
    }

    public Paginated<SubredditImage[]> getSubredditImages(String subredditName) {
        return getSubredditImages(subredditName, SubredditSort.TIME, GalleryWindow.WEEK);
    }

    public Paginated<SubredditImage[]> getSubredditImages(String subredditName, SubredditSort sort, GalleryWindow window) {
        return new Paginated<>((int page) -> getSubredditImages(subredditName, sort, window, page));
    }

    public SubredditImage[] getSubredditImages(String subredditName, SubredditSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String subredditUrl = baseUrlWithMultiplePathParameters("r", subredditName, sort.toString(), window.toString(), page(page));

        return getImgur().HTTP.typedGet(subredditUrl, SubredditImage[].class);
    }

    public SubredditImage getSubredditImage(String subredditName, String imageId) throws ApiRequestException {
        String subredditUrl = baseUrlWithMultiplePathParameters("r", subredditName, imageId);

        return getImgur().HTTP.typedGet(subredditUrl, SubredditImageImpl.class);
    }

    public Paginated<Tag> getTagImages(String tagName) {
        return getTagImages(tagName, TopicSort.VIRAL, GalleryWindow.WEEK);
    }

    public Paginated<Tag> getTagImages(String tagName, TopicSort sort, GalleryWindow window) {
        return new Paginated<>((int page) -> getTagImages(tagName, sort, window, page));
    }

    public Tag getTagImages(String tagName, TopicSort sort, GalleryWindow window, int page) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(tagName, sort.toString(), window.toString(), page(page));

        return getImgur().HTTP.typedGet(tagUrl, Tag.class);
    }

    public GalleryImage getTaggedImage(String tagName, String imageId) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(tagName, imageId);

        return getImgur().HTTP.typedGet(tagUrl, GalleryImageImpl.class);
    }

    public TagVote[] getEntryTags(String entryId) throws ApiRequestException {
        String tagUrl = endpointUrlWithMultiplePathParameters(entryId, "tags");

        return getImgur().HTTP.typedGet(tagUrl, TagVote[].class);
    }

    public void voteOnTag(String entryId, String tagName, boolean upvote) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", "tag", tagName, upvote ? "up" : "down");

        getImgur().HTTP.post(voteUrl);
    }

    public void addTags(String entryId, String[] tagNames) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters("tags", entryId);

        getImgur().HTTP.post(voteUrl, getParamsFor("tags", Utils.asCommaSeparatedList(tagNames)), true);
    }

    // TODO Gallery Search


    public Paginated<GalleryEntry[]> random() throws ApiRequestException {
        return new Paginated<>(this::random);
    }

    public GalleryEntry[] random(int page) throws ApiRequestException {
        String randomUrl = endpointUrlWithMultiplePathParameters("random", "random", page(page));

        return getImgur().HTTP.typedGet(randomUrl, GalleryEntry[].class);
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

        return getImgur().HTTP.typedPost(submitUrl, Boolean.class, params, true);
    }

    public void removeFromGallery(String id) throws ApiRequestException {
        String submitUrl = endpointUrlWithSinglePathParameter(id);

        getImgur().HTTP.delete(submitUrl, true);
    }

    public GalleryAlbum getGalleryAlbum(String albumId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters("album", albumId);

        return getImgur().HTTP.typedGet(albumUrl, GalleryAlbumImpl.class);
    }

    public GalleryImage getGalleryImage(String imageId) throws ApiRequestException {
        String imageUrl = endpointUrlWithMultiplePathParameters("image", imageId);

        return getImgur().HTTP.typedGet(imageUrl, GalleryImageImpl.class);
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

        getImgur().HTTP.post(reportUrl, params);
    }

    public Vote getGalleryEntryVotes(String entryId) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "votes");
        return getImgur().HTTP.typedGet(voteUrl, Vote.class);
    }

    public void voteOnGalleryEntry(String entryId, boolean upvote) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", (upvote ? "up" : "down"));

        getImgur().HTTP.post(voteUrl);
    }

    public void clearVoteOnGalleryEntry(String entryId) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(entryId, "vote", "veto");

        getImgur().HTTP.post(voteUrl);
    }

    public Comment[] albumComments(String id, CommentSort sort) throws ApiRequestException {
        return comments(GalleryEntryType.ALBUM, id, sort);
    }

    public Comment[] imageComments(String id, CommentSort sort) throws ApiRequestException {
        return comments(GalleryEntryType.IMAGE, id, sort);
    }

    private Comment[] comments(GalleryEntryType type, String id, CommentSort sort) throws ApiRequestException {

        String commentsUrl = endpointUrlWithMultiplePathParameters(type.toString(), id, COMMENTS, sort.toString());

        return getImgur().HTTP.typedGet(commentsUrl, Comment[].class);
    }

    public Comment getComment(String entryId, String commentId) throws ApiRequestException {
        String commentUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENT, commentId);

        return getImgur().HTTP.typedGet(commentUrl, Comment.class);
    }

    public String addComment(String entryId, String comment) throws ApiRequestException {
        return replyToComment(entryId, null, comment);
    }

    public String replyToComment(String entryId, String parentCommentId, String comment) throws ApiRequestException {

        String commentUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENT, parentCommentId);

        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair(COMMENT, comment));

        // TODO test
        CommentResponseData commentResponse = getImgur().HTTP.typedPost(commentUrl, CommentResponseData.class, params, true);

        return Long.toString(commentResponse.getId());
    }

    public String[] getCommentIds(String entryId) throws ApiRequestException {
        String commentIdsUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENTS, "ids");

        return getImgur().HTTP.typedGet(commentIdsUrl, String[].class);
    }

    public int getCommentCount(String entryId) throws ApiRequestException {
        String commentCountUrl = endpointUrlWithMultiplePathParameters(entryId, COMMENTS, "count");

        return getImgur().HTTP.typedGet(commentCountUrl, Integer.class);
    }
}

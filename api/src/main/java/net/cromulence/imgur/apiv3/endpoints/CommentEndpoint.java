package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.CommentResponseData;
import net.cromulence.imgur.apiv3.datamodel.constants.ReportReason;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class CommentEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "comment";

    public CommentEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Comment getComment(String commentId) throws ApiRequestException {
        String commentUrl = endpointUrlWithSinglePathParameter(commentId);
        return getImgur().http.typedGet(commentUrl, Comment.class);
    }

    public Comment[] getRepliesToComment(String commentId) throws ApiRequestException {
        String repliesUrl = endpointUrlWithMultiplePathParameters(commentId, "replies");
        return getImgur().http.typedGet(repliesUrl, Comment.class).getChildren();
    }

    public void deleteComment(String commentId) throws ApiRequestException {
        String deleteUrl = endpointUrlWithSinglePathParameter(commentId);

        getImgur().http.delete(deleteUrl);
    }

    public String addComment(String imageId, String comment) throws ApiRequestException {
        return replyToComment(imageId, null, comment);
    }

    public String replyToComment(String imageId, String parentCommentId, String comment) throws ApiRequestException {

        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("image_id", imageId));
        params.add(new BasicNameValuePair("comment", comment));

        if (parentCommentId != null && parentCommentId.length() > 0) {
            params.add(new BasicNameValuePair("parent_id", parentCommentId));
        }

        CommentResponseData commentResponse = getImgur().http.typedPost(getEndpointUrl(), CommentResponseData.class, params, true);

        return Long.toString(commentResponse.getId());
    }

    public void voteOnComment(String commentId, boolean upvote) throws ApiRequestException {
        String voteUrl = endpointUrlWithMultiplePathParameters(commentId, "vote", upvote ? "up" : "down");
        getImgur().http.post(voteUrl);
    }

    public void reportComment(String commentId) throws ApiRequestException {
        reportComment(commentId, null);
    }

    public void reportComment(String commentId, ReportReason reason) throws ApiRequestException {
        String reportUrl = endpointUrlWithMultiplePathParameters(commentId, "report");

        ArrayList<NameValuePair> params = new ArrayList<>();

        if (reason != null) {
            params.add(new BasicNameValuePair("reason", reason.getCode()));
        }

        getImgur().http.post(reportUrl, params, true);
    }
}

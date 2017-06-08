package net.cromulence.imgur.apiv3.endpoints;

import com.google.gson.reflect.TypeToken;

import net.cromulence.imgur.apiv3.api.BasicResponse;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.Conversation;
import net.cromulence.imgur.apiv3.datamodel.Notification;
import net.cromulence.imgur.apiv3.datamodel.Notifications;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class NotificationEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "notification";

    public NotificationEndpoint(Imgur imgur) {
        super(imgur);
    }

    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Notifications getNotifications() throws ApiRequestException {
        return getNotifications(true);
    }

    public Notifications getNotifications(boolean onlyUnviewed) throws ApiRequestException {
        final String notificationsUrl = String.format("%s?new=%b", getEndpointUrl(), onlyUnviewed);

        return getImgur().HTTP.typedGet(notificationsUrl, Notifications.class, true);
    }

    public Notification getNotification(int id) throws ApiRequestException {
        final String notificationsUrl = String.format("%s/%d", getEndpointUrl(), id);

        return getImgur().HTTP.typedGet(notificationsUrl, Notification.class, true);
    }

    public Notification<Comment> getCommentNotification(int id) throws ApiRequestException {
        final String notificationsUrl = String.format("%s/%d", getEndpointUrl(), id);
        return getImgur().HTTP.typedGet(notificationsUrl, new TypeToken<Notification<Comment>>() {
        }, true);
    }

    public Notification<Conversation> getConversationNotification(int id) throws ApiRequestException {
        final String notificationsUrl = String.format("%s/%d", getEndpointUrl(), id);
        return getImgur().HTTP.typedGet(notificationsUrl, new TypeToken<Notification<Conversation>>() {
        }, true);
    }

    public void markRead(int id) throws ApiRequestException {
        final String notificationsUrl = String.format("%s/%d", getEndpointUrl(), id);

        getImgur().HTTP.typedPost(notificationsUrl, Boolean.class, true);
    }

    public void markRead(int... ids) throws ApiRequestException {

        final StringBuilder sb = new StringBuilder();

        for (int i : ids) {
            sb.append(i).append(",");
        }

        if (ids.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        final ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("ids", sb.toString()));

        getImgur().HTTP.typedPost(getEndpointUrl(), BasicResponse.class, params, true);
    }
}
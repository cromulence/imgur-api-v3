package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.Topic;
import net.cromulence.imgur.apiv3.datamodel.constants.TopicSort;
import net.cromulence.imgur.apiv3.datamodel.constants.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TopicEndpoint extends AbstractEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TopicEndpoint.class);

    private static final String ENDPOINT_NAME = "topics";

    public TopicEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Topic[] getDefaultTopics() throws ApiRequestException {
        String url = endpointUrlWithSinglePathParameter("defaults");

        return getImgur().HTTP.typedGet(url, Topic[].class);
    }

    public GalleryEntry[] getTopic(int topicId) throws ApiRequestException {
        return getTopic(topicId, null, -1, null);
    }

    public GalleryEntry[] getTopic(int topicId, TopicSort sort, int page, Window window) throws ApiRequestException {
        return getTopic(Integer.toString(topicId), sort, page, window);
    }

    public GalleryEntry[] getTopic(String topicName) throws ApiRequestException {
        return getTopic(topicName, null, -1, null);
    }

    public GalleryEntry[] getTopic(String topicName, TopicSort sort, int page, Window window) throws ApiRequestException {

        final String urlFormat = "%s/%s%s%s%s";

        String url = String.format(urlFormat, getEndpointUrl(), encode(topicName), (sort == null ? "" : "/" + sort), (page < 0 ? "" : "/" + page), (window == null ? "" : "/" + window));

        return getImgur().HTTP.typedGet(url, GalleryEntry[].class);
    }

    private String encode(String str) throws ApiRequestException {
        try {
            return URLEncoder.encode(str.replace(" ", "_"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            LOG.error("Unable to URLEncode: {}", str, e);
            throw new ApiRequestException("Unable to encode string: " + str, e);
        }
    }

    public GalleryEntry getTopicItem(int topicId, String itemId) throws ApiRequestException {
        return getTopicItem(Integer.toString(topicId), itemId);
    }

    public GalleryEntry getTopicItem(String topicName, String itemId) throws ApiRequestException {
        String urlFormat = "%s/%s/%s";

        String url = String.format(urlFormat, getEndpointUrl(), encode(topicName), itemId);

        return getImgur().HTTP.typedGet(url, GalleryEntry.class, true);
    }
}

package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.Topic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicEndpointIntegrationTest extends ImgurEndpointTest {

    private static Logger LOG = LoggerFactory.getLogger(TopicEndpointIntegrationTest.class);

    @Test
    public void getTopicItems() throws Exception {

        Topic[] defaultTopics = getUser1ImgurUnderTest().TOPIC.getDefaultTopics();

        for (int i = 0; ((i < 5) && (i < defaultTopics.length)); i++) {
            Topic defaultTopic = defaultTopics[i];

            LOG.info("Got topid id:{} name:{}", defaultTopic.getId(), defaultTopic.getName());

            GalleryEntry[] topicItems;

            int topicId = -1;
            String topicName = null;

            if (i % 2 == 0) {
                topicId = defaultTopic.getId();
                topicItems = getUser1ImgurUnderTest().TOPIC.getTopic(topicId);
            } else {
                topicName = defaultTopic.getName();
                topicItems = getUser1ImgurUnderTest().TOPIC.getTopic(topicName);
            }

            for (int j = 0; ((j < 5) && (j < topicItems.length)); j++) {
                GalleryEntry item = topicItems[j];

                GalleryEntry topicItem;

                if (topicName == null) {
                    topicItem = getUser1ImgurUnderTest().TOPIC.getTopicItem(topicId, item.get().getId());
                } else {
                    topicItem = getUser1ImgurUnderTest().TOPIC.getTopicItem(topicName, item.get().getId());
                }

                dump(LOG, "topic item", topicItem);
            }
        }
    }
}
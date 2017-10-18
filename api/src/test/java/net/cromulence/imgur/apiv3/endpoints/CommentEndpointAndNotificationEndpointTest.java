package net.cromulence.imgur.apiv3.endpoints;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.datamodel.Notification;
import net.cromulence.imgur.apiv3.datamodel.Notifications;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetails;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetailsBuilder;

import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentEndpointAndNotificationEndpointTest extends ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(CommentEndpointAndNotificationEndpointTest.class);
    private static final String ALICE_COMMENT = "Alice replies";

    private String uploadId = null;

    @After
    public void deleteImage() throws Exception {
        if (uploadId != null) {
            getUser1ImgurUnderTest().gallery.removeFromGallery(uploadId);

            getUser1ImgurUnderTest().image.deleteImage(uploadId);

            LOG.info("should have deleted image");
        } else {
            LOG.info("no image to delete");
        }
    }

    //@Test
    public void testNotifications() throws Exception {

        {
            // get new notifications for u1
            Notifications initialNotificationsU1 = getUser1ImgurUnderTest().notification.getNotifications(true);

            // confirm no notifications for u1
            assertEquals("Should be no message notifications", initialNotificationsU1.getMessages().length, 0);
            assertEquals("Should be no reply notifications", initialNotificationsU1.getReplies().length, 0);

            // get new notifications for u2
            Notifications initialNotificationsU2 = getUser2ImgurUnderTest().notification.getNotifications(true);

            // confirm no notifications for u2
            assertEquals("Should be no message notifications", initialNotificationsU2.getMessages().length, 0);
            assertEquals("Should be no reply notifications", initialNotificationsU2.getReplies().length, 0);
        }

        // Make a post as user 1
        ImageUploadDetailsBuilder builder = new ImageUploadDetailsBuilder(getClass().getResourceAsStream("/testcard.png"));

        builder.title("test card").description("just a test").name("testcard.png");

        ImageUploadDetails details = builder.build();

        Image upload = getUser1ImgurUnderTest().image.uploadImage(details);

        dump(LOG, "Uploaded image", upload);

        uploadId = upload.getId();

        pause(LOG, "Waiting before submitting to gallery", 20000);

        boolean b = getUser1ImgurUnderTest().gallery.shareImageWithCommunity(upload);

        dump(LOG, "shared to gallery?", b);

        assertTrue("Should have been shared to gallery", b);

        // Wait for it to get to the gallery before looking to comment on it
        pause(LOG, "Waiting before commenting in gallery", 20000);

        {
            dump(LOG, "aliceViewOfImage before approval", getUser1ImgurUnderTest().gallery.getGalleryImage(upload.getId()));

            dump(LOG, "bobViewOfImage before approval", getUser2ImgurUnderTest().gallery.getGalleryImage(upload.getId()));
        }

        pause(LOG, "Waiting before replying to comment", 40000);

        // comment on the post as user 2
        String commentId = getUser2ImgurUnderTest().comment.addComment(upload.getId(), "Bob comments");

        // Wait for the comment to notify
        pause(LOG, "waiting for notification to come thru", 20000);

        {
            // get u1 new notifications, confirm none for comment on post
            Notifications afterComment1NotificationsU1 = getUser1ImgurUnderTest().notification.getNotifications(true);
            assertEquals("Should be no message notifications", 0, afterComment1NotificationsU1.getMessages().length);
            assertEquals("Should be no reply notifications", 0, afterComment1NotificationsU1.getReplies().length);
        }

        // u1 comment on u2 comment
        String replyId = getUser1ImgurUnderTest().comment.replyToComment(upload.getId(), commentId, ALICE_COMMENT);

        {
            // Wait for the comment to notify
            pause(LOG, "Waiting for notification", 20000);

            // get u2 new notifications, should be a reply. confirm content and don't mark read
            Notifications notificationsU2 = getUser2ImgurUnderTest().notification.getNotifications(true);
            assertEquals("Should be no message notifications", 0, notificationsU2.getMessages().length);
            assertEquals("Should be 1 reply notifications", 1, notificationsU2.getReplies().length);

            // u1 upvote u2 comment
            getUser1ImgurUnderTest().comment.voteOnComment(commentId, true);

            Comment comment = getUser1ImgurUnderTest().comment.getComment(commentId);

            // make sure it has 2 and 2 score
            assertEquals("should have been upvoted", 2, (int) comment.getPoints());
            assertEquals("should have been upvoted", 2, comment.getUps());
            assertEquals("should have been upvoted", 0, comment.getDowns());

            // u2 downvote u1 comment
            getUser2ImgurUnderTest().comment.voteOnComment(replyId, false);

            Comment[] replies = getUser1ImgurUnderTest().comment.getRepliesToComment(commentId);

            assertEquals("should be 1 reply", 1, replies.length);

            Comment reply = replies[0];

            // make sure it has 2 votes and 0 score
            assertEquals("should have been downvoted", 0, (int) reply.getPoints());
            assertEquals("should have been downvoted", 1, reply.getUps());
            assertEquals("should have been downvoted", 1, reply.getDowns());
        }

        {
            // get u2 all notifications, should still be some unread
            Notifications notificationsU2 = getUser2ImgurUnderTest().notification.getNotifications(true);
            assertEquals("Should be no message notifications", 0, notificationsU2.getMessages().length);
            assertEquals("Should be 1 reply notifications", 1, notificationsU2.getReplies().length);

            // get notification and confirm content
            Notification<Comment> notification = getUser2ImgurUnderTest().notification.getCommentNotification(notificationsU2.getReplies()[0].getId());

            Comment content = notification.getContent();
            assertEquals("comment should be as posted", ALICE_COMMENT, content.getComment());

            // mark read
            getUser2ImgurUnderTest().notification.markRead(notificationsU2.getReplies()[0].getId());
        }

        {
            // get u2 new notifications, should be none
            Notifications notificationsU2 = getUser2ImgurUnderTest().notification.getNotifications(true);
            assertEquals("Should be no message notifications", 0, notificationsU2.getMessages().length);
            assertEquals("Should be no reply notifications", 0, notificationsU2.getReplies().length);
        }

        // delete u1 reply on u2 comment
        getUser1ImgurUnderTest().comment.deleteComment(replyId);

        Comment[] replies = getUser1ImgurUnderTest().comment.getRepliesToComment(commentId);

        assertEquals("should be no replies", 0, replies.length);

        {
            dump(LOG, "aliceViewOfImage after approval", getUser1ImgurUnderTest().gallery.getGalleryImage(upload.getId()));

            dump(LOG, "bobViewOfImage after approval", getUser2ImgurUnderTest().gallery.getGalleryImage(upload.getId()));
        }


    }
}

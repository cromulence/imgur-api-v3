package net.cromulence.imgur.apiv3.endpoints;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetails;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetailsBuilder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageEndpointIntegrationTest extends ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(ImageEndpointIntegrationTest.class);
    private static final String NEW_TITLE = "newtitle";
    private String NEW_DESCRIPTION = "newdescription";

    @Test
    public void getAnonymousImageMethods() throws Exception {

        getUser1ImgurUnderTest().AUTH_UTILS.refreshToken();

        // upload
        ImageUploadDetailsBuilder builder = new ImageUploadDetailsBuilder(getClass().getResourceAsStream("/testcard.png"));

        builder.title("test card").description("just a test").name("testcard.png");

        ImageUploadDetails details = builder.build();

        Image anonymousImage = getUser1ImgurUnderTest().IMAGE.uploadAnonymousImage(details);

        final String id = anonymousImage.getId();

        {
            // get
            Image image = getUser1ImgurUnderTest().IMAGE.getImage(id);

            // confirm same
            assertEquals("title should be as expected", details.getTitle(), anonymousImage.getTitle());
            assertEquals("description should be as expected", details.getDescription(), anonymousImage.getDescription());
        }

        // update info
        getUser1ImgurUnderTest().IMAGE.updateAnonymousImageInfo(anonymousImage.getDeleteHash(), NEW_TITLE, NEW_DESCRIPTION);

        {
            // get
            Image image = getUser1ImgurUnderTest().IMAGE.getImage(id);

            // confirm same
            assertEquals("title should be as expected", NEW_TITLE, image.getTitle());
            assertEquals("description should be as expected", NEW_DESCRIPTION, image.getDescription());
        }

        // delete
        getUser1ImgurUnderTest().IMAGE.deleteAnonymousImage(anonymousImage.getDeleteHash());

        // get fails
        {
            try {
                // get
                getUser1ImgurUnderTest().IMAGE.getImage(id);
                fail("should have had exception");
            } catch (Exception e) {
                // ignore
            }
        }
    }

    @Test
    public void getAuthenticatedImageMethods() throws Exception {

        getUser1ImgurUnderTest().AUTH_UTILS.refreshToken();

        // upload
        ImageUploadDetailsBuilder builder = new ImageUploadDetailsBuilder(getClass().getResourceAsStream("/testcard.png"));

        builder.title("test card").description("just a test").name("testcard.png");

        ImageUploadDetails details = builder.build();

        Image authImage = getUser1ImgurUnderTest().IMAGE.uploadImage(details);

        final String id = authImage.getId();

        {
            // get
            Image image = getUser1ImgurUnderTest().IMAGE.getImage(id);

            // confirm same
            assertEquals("title should be as expected", details.getTitle(), authImage.getTitle());
            assertEquals("description should be as expected", details.getDescription(), authImage.getDescription());
        }

        {
            // user 2 favourites
            getUser2ImgurUnderTest().IMAGE.favoriteImage(id);

            Image image = getUser2ImgurUnderTest().IMAGE.getImage(id);

            assertTrue("should be a favourite", image.isFavorite());
        }
        // update info
        getUser1ImgurUnderTest().IMAGE.updateImageInfo(id, NEW_TITLE, NEW_DESCRIPTION);

        {
            // get
            Image image = getUser1ImgurUnderTest().IMAGE.getImage(id);

            // confirm updated
            assertEquals("title should be as expected", NEW_TITLE, image.getTitle());
            assertEquals("description should be as expected", NEW_DESCRIPTION, image.getDescription());
        }

        // delete
        getUser1ImgurUnderTest().IMAGE.deleteImage(authImage.getId());

        // get fails
        {
            try {
                // get
                getUser1ImgurUnderTest().IMAGE.getImage(id);
                fail("should have had exception");
            } catch (Exception e) {
                // ignore
            }
        }
    }

}

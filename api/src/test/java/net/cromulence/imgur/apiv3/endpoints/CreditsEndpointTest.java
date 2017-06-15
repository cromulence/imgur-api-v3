package net.cromulence.imgur.apiv3.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.cromulence.imgur.apiv3.datamodel.Credits;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;

import org.junit.Test;

public class CreditsEndpointTest extends ImgurEndpointTest {
    @Test
    public void getCredits() throws Exception {
        Credits userOneInitialCredits = getUser1ImgurUnderTest().CREDITS.getCredits();
        Credits userTwoInitialCredits = getUser2ImgurUnderTest().CREDITS.getCredits();

        Credits userOneBeforeGetImageCredits = getUser1ImgurUnderTest().CREDITS.getCredits();

        // check credits unchanged
        assertEquals("client limit should remain the same", userOneInitialCredits.getClientLimit(), userOneBeforeGetImageCredits.getClientLimit());
        assertEquals("credits should be free", userOneInitialCredits.getClientRemaining(), userOneBeforeGetImageCredits.getClientRemaining());
        assertEquals("user limit should remain the same", userOneInitialCredits.getUserLimit(), userOneBeforeGetImageCredits.getUserLimit());
        assertEquals("credits should be free", userOneInitialCredits.getUserRemaining(), userOneBeforeGetImageCredits.getUserRemaining());

        Paginated<GalleryEntry[]> gallery = getUser1ImgurUnderTest().GALLERY.gallery();
        GalleryEntry[] one = gallery.next();
        GalleryEntry[] two = gallery.next();

        Credits userOneAfterGetImageCredits = getUser1ImgurUnderTest().CREDITS.getCredits();
        Credits userTwoAfterGetImageCredits = getUser2ImgurUnderTest().CREDITS.getCredits();

        // check user one and client credits decreased
        assertEquals("client limit should remain the same", userOneAfterGetImageCredits.getClientLimit(), userOneBeforeGetImageCredits.getClientLimit());
        assertTrue("client should have used some credits", userOneAfterGetImageCredits.getClientRemaining() < userOneBeforeGetImageCredits.getClientRemaining());
        assertEquals("limit should remain the same", userOneAfterGetImageCredits.getUserLimit(), userOneBeforeGetImageCredits.getUserLimit());
        assertTrue("user should have used some credits", userOneAfterGetImageCredits.getUserRemaining() < userOneBeforeGetImageCredits.getUserRemaining());

        assertEquals("client limit should remain the same", userTwoAfterGetImageCredits.getClientLimit(), userTwoInitialCredits.getClientLimit());
        assertTrue("client should have used some credits", userTwoAfterGetImageCredits.getClientRemaining() < userTwoInitialCredits.getClientRemaining());
        assertEquals("user limit should remain the same", userTwoAfterGetImageCredits.getUserLimit(), userTwoInitialCredits.getUserLimit());
        assertEquals("user should have used no credits", userTwoAfterGetImageCredits.getUserRemaining(), userTwoInitialCredits.getUserRemaining());
    }

    //@Test
    public void getAppCredits() throws Exception {
        // TODO is this even a thing?
    }

}
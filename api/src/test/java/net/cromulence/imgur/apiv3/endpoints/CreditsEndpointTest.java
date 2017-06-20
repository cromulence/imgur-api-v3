package net.cromulence.imgur.apiv3.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import net.cromulence.imgur.apiv3.datamodel.Credits;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditsEndpointTest extends ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(CreditsEndpointTest.class);

    @Test
    public void getCredits() throws Exception {
        Credits userOneInitialCredits = getUser1ImgurUnderTest().credits.getCredits();
        Credits userTwoInitialCredits = getUser2ImgurUnderTest().credits.getCredits();

        Credits userOneBeforeGetImageCredits = getUser1ImgurUnderTest().credits.getCredits();

        // check credits unchanged
        assertEquals("client limit should remain the same", userOneInitialCredits.getClientLimit(), userOneBeforeGetImageCredits.getClientLimit());
        assertEquals("credits should be free", userOneInitialCredits.getClientRemaining(), userOneBeforeGetImageCredits.getClientRemaining());
        assertEquals("user limit should remain the same", userOneInitialCredits.getUserLimit(), userOneBeforeGetImageCredits.getUserLimit());
        assertEquals("credits should be free", userOneInitialCredits.getUserRemaining(), userOneBeforeGetImageCredits.getUserRemaining());

        Paginated<GalleryEntry[]> gallery = getUser1ImgurUnderTest().gallery.gallery();
        gallery.next();
        GalleryEntry[] next = gallery.next();

        Credits userOneAfterGetImageCredits = getUser1ImgurUnderTest().credits.getCredits();
        Credits userTwoAfterGetImageCredits = getUser2ImgurUnderTest().credits.getCredits();

        // check user one and client credits decreased
        assertEquals("client limit should remain the same", userOneAfterGetImageCredits.getClientLimit(), userOneBeforeGetImageCredits.getClientLimit());
        assertTrue("client should have used some credits", userOneAfterGetImageCredits.getClientRemaining() < userOneBeforeGetImageCredits.getClientRemaining());
        assertEquals("limit should remain the same", userOneAfterGetImageCredits.getUserLimit(), userOneBeforeGetImageCredits.getUserLimit());
        assertTrue("user should have used some credits", userOneAfterGetImageCredits.getUserRemaining() < userOneBeforeGetImageCredits.getUserRemaining());

        assertEquals("client limit should remain the same", userTwoAfterGetImageCredits.getClientLimit(), userTwoInitialCredits.getClientLimit());
        assertTrue("client should have used some credits", userTwoAfterGetImageCredits.getClientRemaining() < userTwoInitialCredits.getClientRemaining());
        assertEquals("user limit should remain the same", userTwoAfterGetImageCredits.getUserLimit(), userTwoInitialCredits.getUserLimit());
        assertEquals("user should have used no credits", userTwoAfterGetImageCredits.getUserRemaining(), userTwoInitialCredits.getUserRemaining());

        // Update bio and check the post limit decreases

        // Can't use the first one because the limit may be -1 before it is set
        getUser1ImgurUnderTest().credits.getCredits();

        String originalBio = getUser1ImgurUnderTest().account.getBaseInfo().getBio();

        getUser1ImgurUnderTest().account.updateBio("new bio");

        Credits userOneMidBioChangeCredits = getUser1ImgurUnderTest().credits.getCredits();

        getUser1ImgurUnderTest().account.updateBio(originalBio);

        Credits userOneAfterBioChangeCredits = getUser1ImgurUnderTest().credits.getCredits();

        assertEquals("post limit should remain the same", userTwoAfterGetImageCredits.getPostLimit(), userTwoInitialCredits.getPostLimit());
        assertTrue("user should have used some post credits", userOneAfterBioChangeCredits.getPostRemaining() < userOneMidBioChangeCredits.getPostRemaining());
    }
}
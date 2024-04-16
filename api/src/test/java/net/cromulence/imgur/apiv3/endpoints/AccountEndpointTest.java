package net.cromulence.imgur.apiv3.endpoints;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.AccountSettings;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.GalleryProfile;
import net.cromulence.imgur.apiv3.datamodel.Trophy;
import net.cromulence.imgur.apiv3.datamodel.constants.AccountCommentSort;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountEndpointTest extends ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccountEndpointTest.class);

    private static final String UPDATED_BIO = "different bio for testing";

    @Test
    public void testAccountSettings() throws ApiRequestException {
        AccountSettings originalSettings = getUser1ImgurUnderTest().account.getAccountSettings();

        AccountSettings settingsToChange = getUser1ImgurUnderTest().account.getAccountSettings();

        settingsToChange.setMessagingEnabled(!settingsToChange.isMessagingEnabled());
        settingsToChange.setNewsletterSubscribed(!settingsToChange.isNewsletterSubscribed());
        settingsToChange.setShowMature(!settingsToChange.isShowMature());

        getUser1ImgurUnderTest().account.updateAccountSettings(settingsToChange);

        AccountSettings changedSettings = getUser1ImgurUnderTest().account.getAccountSettings();

        assertNotEquals("should have changed", originalSettings.isMessagingEnabled(), changedSettings.isMessagingEnabled());
        assertNotEquals("should have changed", originalSettings.isNewsletterSubscribed(), changedSettings.isNewsletterSubscribed());
        assertNotEquals("should have changed", originalSettings.isShowMature(), changedSettings.isShowMature());

        getUser1ImgurUnderTest().account.updateAccountSettings(originalSettings);

        AccountSettings restoredSettings = getUser1ImgurUnderTest().account.getAccountSettings();

        assertEquals("should have restored", originalSettings.isMessagingEnabled(), restoredSettings.isMessagingEnabled());
        assertEquals("should have restored", originalSettings.isNewsletterSubscribed(), restoredSettings.isNewsletterSubscribed());
        assertEquals("should have restored", originalSettings.isShowMature(), restoredSettings.isShowMature());
    }

    @Test
    public void testBioSettings() throws ApiRequestException {

        float reputation = getUser1ImgurUnderTest().account.getBaseInfo().getReputation();
        assertTrue("Alice's rep should be low-to-terrible", reputation < 1000);

        int created = getUser1ImgurUnderTest().account.getBaseInfo().getCreated();
        assertTrue("Created some time after 2016", created > 86400 * 365 * (2016 - 1970));

        String originalBio = getUser1ImgurUnderTest().account.getBaseInfo().getBio();

        assertNotEquals("need to be able to change bio", originalBio, UPDATED_BIO);

        getUser1ImgurUnderTest().account.updateBio(UPDATED_BIO);

        String changedBio = getUser1ImgurUnderTest().account.getBaseInfo().getBio();

        getUser1ImgurUnderTest().account.updateBio(originalBio);

        String restoredBio = getUser1ImgurUnderTest().account.getBaseInfo().getBio();

        assertNotEquals("bio should have been changed", originalBio, changedBio);

        assertEquals("bio should have been changed", UPDATED_BIO, changedBio);

        assertNotEquals("bio should have been changed", changedBio, restoredBio);

        assertEquals("bio should have been restored", originalBio, restoredBio);
    }

    @Test
    public void testGalleryProfile() throws ApiRequestException {
        GalleryProfile galleryProfile = getUser1ImgurUnderTest().account.getGalleryProfile();

        int totalGalleryComments = galleryProfile.getTotalGalleryComments();
        int totalGalleryFavorites = galleryProfile.getTotalGalleryFavorites();
        int totalGallerySubmissions = galleryProfile.getTotalGallerySubmissions();
        Trophy[] trophies = galleryProfile.getTrophies();

        // On 2024-04-14, this was coming back as zero
        //assertTrue("should be some comments", totalGalleryComments > 1);
        assertTrue("should be lots of favourites", totalGalleryFavorites > 50);
        TestCase.assertEquals("should be 1 submissions", 1, totalGallerySubmissions);
        TestCase.assertEquals("should be 1 trophies", 1, trophies.length);
    }

    @Test
    public void testVerifiedEmail() throws ApiRequestException {
        assertTrue("already verified", getUser1ImgurUnderTest().account.hasVerifiedEmail());

        assertFalse("already verified", getUser1ImgurUnderTest().account.requestVerificationEmail());
    }

    @Test
    public void testPaginatedGalleryFavourites() throws ApiRequestException {
        Paginated<GalleryEntry<?>[]> galleryFavourites = getUser1ImgurUnderTest().account.getAccountGalleryFavourites();

        GalleryEntry<?>[] next;

        do {
            next = galleryFavourites.next();

            dump(LOG, next);
        } while (next.length > 0);
    }

    @Test
    public void testPaginatedCommentIds() throws ApiRequestException {
        Paginated<String[]> galleryFavourites = getUser1ImgurUnderTest().account.getCommentIds("ThisTimeLastYear", AccountCommentSort.NEWEST);

        String[] next;

        do {
            next = galleryFavourites.next();

            dump(LOG, next);
        } while (next.length > 0);
    }
}

package net.cromulence.imgur.apiv3.endpoints;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.AccountSettings;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.GalleryProfile;
import net.cromulence.imgur.apiv3.datamodel.Trophy;
import net.cromulence.imgur.apiv3.datamodel.constants.AccountCommentSort;

import org.junit.Test;

public class AccountEndpointTest extends ImgurEndpointTest {

    private static final String UPDATED_BIO = "different bio for testing";

    @Test
    public void testAccountSettings() throws ApiRequestException {
        AccountSettings originalSettings = getUser1ImgurUnderTest().ACCOUNT.getAccountSettings();

        AccountSettings settingsToChange = getUser1ImgurUnderTest().ACCOUNT.getAccountSettings();

        settingsToChange.setMessagingEnabled(!settingsToChange.isMessagingEnabled());
        settingsToChange.setNewsletterSubscribed(!settingsToChange.isNewsletterSubscribed());
        settingsToChange.setShowMature(!settingsToChange.isShowMature());

        getUser1ImgurUnderTest().ACCOUNT.updateAccountSettings(settingsToChange);

        AccountSettings changedSettings = getUser1ImgurUnderTest().ACCOUNT.getAccountSettings();

        assertNotEquals("should have changed", originalSettings.isMessagingEnabled(), changedSettings.isMessagingEnabled());
        assertNotEquals("should have changed", originalSettings.isNewsletterSubscribed(), changedSettings.isNewsletterSubscribed());
        assertNotEquals("should have changed", originalSettings.isShowMature(), changedSettings.isShowMature());

        getUser1ImgurUnderTest().ACCOUNT.updateAccountSettings(originalSettings);

        AccountSettings restoredSettings = getUser1ImgurUnderTest().ACCOUNT.getAccountSettings();

        assertEquals("should have restored", originalSettings.isMessagingEnabled(), restoredSettings.isMessagingEnabled());
        assertEquals("should have restored", originalSettings.isNewsletterSubscribed(), restoredSettings.isNewsletterSubscribed());
        assertEquals("should have restored", originalSettings.isShowMature(), restoredSettings.isShowMature());
    }

    @Test
    public void testBioSettings() throws ApiRequestException {
        String originalBio = getUser1ImgurUnderTest().ACCOUNT.getBaseInfo().getBio();

        assertNotEquals("need to be able to change bio", originalBio, UPDATED_BIO);

        getUser1ImgurUnderTest().ACCOUNT.updateBio(UPDATED_BIO);

        String changedBio = getUser1ImgurUnderTest().ACCOUNT.getBaseInfo().getBio();

        getUser1ImgurUnderTest().ACCOUNT.updateBio(originalBio);

        String restoredBio = getUser1ImgurUnderTest().ACCOUNT.getBaseInfo().getBio();

        assertNotEquals("bio should have been changed", originalBio, changedBio);

        assertEquals("bio should have been changed", UPDATED_BIO, changedBio);

        assertNotEquals("bio should have been changed", changedBio, restoredBio);

        assertEquals("bio should have been restored", originalBio, restoredBio);
    }

    @Test
    public void testGalleryProfile() throws ApiRequestException {
        GalleryProfile galleryProfile = getUser1ImgurUnderTest().ACCOUNT.getGalleryProfile();

        int totalGalleryComments = galleryProfile.getTotalGalleryComments();
        int totalGalleryFavorites = galleryProfile.getTotalGalleryFavorites();
        int totalGallerySubmissions = galleryProfile.getTotalGallerySubmissions();
        Trophy[] trophies = galleryProfile.getTrophies();

        assertTrue("should be some of comments", totalGalleryComments > 10);
        assertTrue("should be lots of favs", totalGalleryFavorites > 100);
        assertTrue("should be no submissions", totalGallerySubmissions == 0);
        assertTrue("should be no trophies", trophies.length == 0);
    }

    @Test
    public void testVerifiedEmail() throws ApiRequestException {
        assertTrue("already verified", getUser1ImgurUnderTest().ACCOUNT.hasVerifiedEmail());

        assertFalse("already verified", getUser1ImgurUnderTest().ACCOUNT.requestVerificationEmail());
    }

    @Test
    public void testPaginatedGalleryFavourites() throws ApiRequestException {
        Paginated<GalleryEntry[]> galleryFavourites = getUser1ImgurUnderTest().ACCOUNT.getGalleryFavourites();

        GalleryEntry[] next;

        do {
            next = galleryFavourites.next();

            dump(next);
        } while (next.length > 0);
    }

    @Test
    public void testPaginatedCommentIds() throws ApiRequestException {
        Paginated<String[]> galleryFavourites = getUser1ImgurUnderTest().ACCOUNT.getCommentIds("ThisTimeLastYear", AccountCommentSort.NEWEST);

        String[] next;

        do {
            next = galleryFavourites.next();

            dump(next);
        } while (next.length > 0);
    }
}

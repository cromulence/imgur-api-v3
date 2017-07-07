package net.cromulence.imgur.apiv3.endpoints;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.api.exceptions.NotFoundException;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.Vote;
import net.cromulence.imgur.apiv3.datamodel.constants.GallerySort;
import net.cromulence.imgur.apiv3.datamodel.constants.GalleryWindow;
import net.cromulence.imgur.apiv3.datamodel.constants.Section;
import net.cromulence.imgur.apiv3.datamodel.constants.SubredditSort;
import net.cromulence.imgur.apiv3.datamodel.constants.TopicSort;
import net.cromulence.imgur.apiv3.datamodel.constants.Window;
import net.cromulence.imgur.apiv3.datamodel.meme.MemeEntry;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditAlbum;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditEntry;
import net.cromulence.imgur.apiv3.datamodel.subreddit.SubredditImage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GalleryEndpointTest extends ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(GalleryEndpointTest.class);

    // Meme stuff seems to have vanished from the API on the new apidocs site. The endpoint still works, but there is no MemeMetadata now.
//    @Test
//    public void testMemes() throws ApiRequestException {
//
//        Paginated<MemeEntry[]> memes = getUser1ImgurUnderTest().gallery.getMemes(TopicSort.VIRAL, GalleryWindow.ALL);
//
//        int imageCount = 0;
//
//        while(imageCount <= 10) {
//            MemeEntry[] next = memes.next();
//
//            for (MemeEntry fromArray : next) {
//
//                String id = fromArray.getId();
//
//                if (fromArray.isAlbum()) {
//                    System.out.println("skipping meme album");
//                } else {
//                    if(fromArray.getMetadata() == null) {
//                        System.out.println("skipping non memegen image");
//                        continue;
//                    } else {
//                        MemeEntry direct = getUser1ImgurUnderTest().gallery.getMemeImage(id);
//
//                        assertEquals("paginated meme and direct meme should be the same", fromArray.getAccountId(), direct.getAccountId());
//                        assertEquals("paginated meme and direct meme should be the same", fromArray.getDatetime(), direct.getDatetime());
//                        assertEquals("paginated meme and direct meme should be the same", fromArray.getTitle(), direct.getTitle());
//                        assertEquals("paginated meme and direct meme should be the same", fromArray.getTopic(), direct.getTopic());
//
//                        imageCount++;
//                    }
//                }
//
//                if(imageCount > 10) {
//                    break;
//                }
//
//            }
//        }
//
//    }

    @Test
    public void testSubreddits() throws ApiRequestException {

        // Awful, non-repeatable test. Depends on the state of the top 5 things in /r/pics. Bleh. Such is life, working against a live API

        Paginated<SubredditEntry[]> images = getUser1ImgurUnderTest().gallery.getSubredditGalleries( "pics", SubredditSort.TIME, GalleryWindow.WEEK);

        SubredditEntry[] next = images.next();

        int compared = 0;

        for(SubredditEntry fromArray : next) {

            if (fromArray.isAlbum()) {
                LOG.info("skipping subreddit album");
                //SubredditAlbum direct = getUser1ImgurUnderTest().gallery.getSubredditAlbum("pics", fromArray.getId());

            } else {
                SubredditImage direct = getUser1ImgurUnderTest().gallery.getSubredditImage("pics", fromArray.getId());
                assertEquals("paginated meme and direct meme should be the same", fromArray.getAccountId(), direct.getAccountId());
                assertEquals("paginated meme and direct meme should be the same", fromArray.getDatetime(), direct.getDatetime());
                assertEquals("paginated meme and direct meme should be the same", fromArray.getTitle(), direct.getTitle());
                assertEquals("paginated meme and direct meme should be the same", fromArray.getTopic(), direct.getTopic());
                compared++;
            }

            if(compared == 5) {
                break;
            }
        }
    }

    @Test
    public void testVotes() throws ApiRequestException {
        GalleryEntry[] gallery = getUser1ImgurUnderTest().gallery.gallery(Section.HOT, GallerySort.VIRAL, GalleryWindow.DAY, 0, true);

        if(gallery.length == 0) {
            fail("No images in gallery");
        }

        String id = gallery[0].getId();
        int ups = gallery[0].getUps();
        int downs = gallery[0].getDowns();

        Vote galleryEntryVotes = getUser2ImgurUnderTest().gallery.getGalleryEntryVotes(id);

        TestCase.assertEquals("Ups should be the same", ups, galleryEntryVotes.getUps());
        TestCase.assertEquals("Downs should be the same", downs, galleryEntryVotes.getDowns());
    }
}





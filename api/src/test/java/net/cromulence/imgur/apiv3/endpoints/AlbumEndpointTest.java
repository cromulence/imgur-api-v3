package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.AlbumIds;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumPrivacy;
import net.cromulence.imgur.apiv3.endpoints.params.AlbumCreationDetailsBuilder;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetails;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetailsBuilder;

import org.junit.Test;

public class AlbumEndpointTest extends ImgurEndpointTest {


    @Test
    public void testCreateAlbum() throws ApiRequestException {

        String id1 = null;
        String id2 = null;
        String id3 = null;

        AlbumIds albumIds = null;

        try {
            // Upload 3 images
            id1 = uploadImage(getUser1ImgurUnderTest(), "1.png", "title one", "description one");
            id2 = uploadImage(getUser1ImgurUnderTest(), "2.png", "title two", "description two");
            id3 = uploadImage(getUser1ImgurUnderTest(), "3.png", "title three", "description three");

            // Create an album containing the images

            AlbumCreationDetailsBuilder b = new AlbumCreationDetailsBuilder();

            b.coverId(id2).title("album title").description("album description").privacy(AlbumPrivacy.HIDDEN).layout(AlbumLayout.GRID).imageIds(new String[] {id1, id2, id3});

            // publish the album
            albumIds = getUser1ImgurUnderTest().album.createAlbum(b.build());

            // get the album
            Album retrievedAlbum = getUser1ImgurUnderTest().album.getAlbum(albumIds.getId());

            // confirm the details of the album
            // TODO

            // confirm the details of the images in the album
            Image[] retrievedImages = retrievedAlbum.getImages();
            // TODO
        } finally {
            // delete the album
            if (albumIds != null) {
                try {
                    getUser1ImgurUnderTest().album.deleteAlbum(albumIds.getId());
                } catch (ApiRequestException e) {
                    System.out.println("Unable to delete album");
                }
            }

            // Delete the images
            if (id1 != null) {
                try {
                    getUser1ImgurUnderTest().image.deleteImage(id1);
                } catch (ApiRequestException e) {
                    System.out.println("Unable to delete album");
                }
            }

            if (id2 != null) {
                try {
                    getUser1ImgurUnderTest().image.deleteImage(id2);
                } catch (ApiRequestException e) {
                    System.out.println("Unable to delete album");
                }
            }

            if (id3 != null) {
                try {
                    getUser1ImgurUnderTest().image.deleteImage(id3);
                } catch (ApiRequestException e) {
                    System.out.println("Unable to delete album");
                }
            }
        }

    }

    private String uploadImage(Imgur imgur, String filename, String title, String description) throws ApiRequestException {
        // upload
        ImageUploadDetailsBuilder builder = new ImageUploadDetailsBuilder(getClass().getResourceAsStream("/" + filename));

        builder.title(title).description(description).name(filename);

        ImageUploadDetails details = builder.build();

        Image image = imgur.image.uploadImage(details);

        return image.getId();
    }
}

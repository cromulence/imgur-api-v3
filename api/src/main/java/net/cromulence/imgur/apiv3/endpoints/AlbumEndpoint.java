package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.Utils;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.AlbumIds;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.endpoints.params.AlbumCreationDetails;

import org.apache.http.NameValuePair;

import java.util.List;

public class AlbumEndpoint extends AbstractEndpoint {

    public AlbumEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "album";
    }

    public Album getAlbum(String albumId) throws ApiRequestException {
        final String albumUrl = endpointUrlWithSinglePathParameter(albumId);

        return getImgur().http.typedGet(albumUrl, Album.class);
    }

    public Image[] getImages(String albumId) throws ApiRequestException {
        final String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "images");

        return getImgur().http.typedGet(albumUrl, Image[].class);
    }

    public Image getImage(String albumId, String imageId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "image", imageId);

        return getImgur().http.typedGet(albumUrl, Image.class);
    }

    public AlbumIds createAlbum(AlbumCreationDetails details) throws ApiRequestException {
        return doCreateAlbum(details, true);
    }

    public AlbumIds createAlbumAnonymous(AlbumCreationDetails details) throws ApiRequestException {
        return doCreateAlbum(details, false);
    }

    private AlbumIds doCreateAlbum(AlbumCreationDetails details, boolean authenticated) throws ApiRequestException {
        return getImgur().http.typedPost(getEndpointUrl(), AlbumIds.class, details.getAsParams(), authenticated);
    }

    public void updateAlbum(String albumId, AlbumCreationDetails details) throws ApiRequestException {
        doUpdateAlbum(albumId, details, true);
    }

    public void updateAlbumAnonymous(String albumId, AlbumCreationDetails details) throws ApiRequestException {
        doUpdateAlbum(albumId, details, false);
    }

    private void doUpdateAlbum(String albumId, AlbumCreationDetails details, boolean authenticated) throws ApiRequestException {
        final String updateUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().http.post(updateUrl, details.getAsParams(), authenticated);
    }

    public void deleteAlbum(String albumId) throws ApiRequestException {
        doDeleteAlbum(albumId, true);
    }

    public void deleteAlbumAnonymous(String deleteHash) throws ApiRequestException {
        doDeleteAlbum(deleteHash, false);
    }

    private void doDeleteAlbum(String albumId, boolean authenticated) throws ApiRequestException {
        final String albumUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().http.delete(albumUrl, authenticated);
    }

    public void favourite(String albumId) throws ApiRequestException {
        final String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "favorite");

        getImgur().http.post(albumUrl, true);
    }

    public void setAlbumsImages(String albumId, String[] imageIds) throws ApiRequestException {
        doSetAlbumImages(albumId, imageIds,true);
    }

    public void setAlbumImagesAnonymous(String deleteHash, String[] imageDeleteHashes) throws ApiRequestException {
        doSetAlbumImages(deleteHash, imageDeleteHashes,false);
    }

    private void doSetAlbumImages(String id, String[] imageIds, boolean authenticated) throws ApiRequestException {
        final String setUrl = endpointUrlWithSinglePathParameter(id);

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.post(setUrl, params, authenticated);
    }

    public void addImages(String albumId, String[] imageIds) throws ApiRequestException {
        doAddImages(albumId, imageIds, true);
    }

    public void addImagesAnonymous(String deleteHash, String[] imageDeleteHashes) throws ApiRequestException {
        doAddImages(deleteHash, imageDeleteHashes, true);
    }

    private void doAddImages(String albumId, String[] imageIds, boolean authenticated) throws ApiRequestException {
        final String addUrl = endpointUrlWithMultiplePathParameters(albumId, "add");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.post(addUrl, params, authenticated);
    }

    public void removeImages(String albumId, String[] imageIds) throws ApiRequestException {
        doRemoveImages(albumId, imageIds, true);
    }

    public void removeImagesAnonymous(String deleteHash, String[] imageDeleteHashes) throws ApiRequestException {
        doRemoveImages(deleteHash, imageDeleteHashes, false);
    }

    private void doRemoveImages(String albumId, String[] imageIds, boolean authenticated) throws ApiRequestException {
        final String addUrl = endpointUrlWithMultiplePathParameters(albumId, "remove_images");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.delete(addUrl, params, authenticated);
    }
}



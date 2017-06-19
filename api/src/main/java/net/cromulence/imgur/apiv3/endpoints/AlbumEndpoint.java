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
        return getImgur().http.typedPost(getEndpointUrl(), AlbumIds.class, details.getAsParams(), true);
    }

    public void updateAlbum(String albumId, AlbumCreationDetails details) throws ApiRequestException {
        final String updateUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().http.post(updateUrl, details.getAsParams(), true);
    }

    public AlbumIds createAnonymousAlbum(AlbumCreationDetails details) throws ApiRequestException {
        return getImgur().http.typedPost(getEndpointUrl(), AlbumIds.class, details.getAsParams(), false);
    }

    public void updateAnonymousAlbum(String albumId, AlbumCreationDetails details) throws ApiRequestException {
        final String updateUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().http.post(updateUrl, details.getAsParams(), false);
    }

    public void deleteAlbum(String albumId) throws ApiRequestException {
        final String albumUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().http.delete(albumUrl);
    }

    public void favourite(String albumId) throws ApiRequestException {
        final String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "favorite");

        getImgur().http.post(albumUrl, true);
    }

    public void setAlbumsImages(String albumId, String[] imageIds) throws ApiRequestException {
        final String setUrl = endpointUrlWithSinglePathParameter(albumId);

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.post(setUrl, params, true);
    }

    public void setAnonymousAlbumImages(String deleteHash, String[] imageDeleteHashes) throws ApiRequestException {
        final String setUrl = endpointUrlWithSinglePathParameter(deleteHash);

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageDeleteHashes));

        getImgur().http.post(setUrl, params, false);
    }

//    TODO Set Album Images anonymous

    public void addImages(String albumId, String[] imageIds) throws ApiRequestException {
        final String addUrl = endpointUrlWithMultiplePathParameters(albumId, "add");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.post(addUrl, params, true);
    }

    public void removeImages(String albumId, String[] imageIds) throws ApiRequestException {
        final String addUrl = endpointUrlWithMultiplePathParameters(albumId, "remove_images");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().http.delete(addUrl, params, true);
    }

    public void deleteAnonymousAlbum(String deleteHash) throws ApiRequestException {
        final String albumUrl = endpointUrlWithSinglePathParameter(deleteHash);

        getImgur().http.delete(albumUrl, false);
    }

//    TODO Add Images to an Album anonymous
//    TODO Remove Images from an Album anonymous
}



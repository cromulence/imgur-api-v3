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
        String albumUrl = endpointUrlWithSinglePathParameter(albumId);

        return getImgur().HTTP.typedGet(albumUrl, Album.class);
    }

    public Image[] getImages(String albumId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "images");

        return getImgur().HTTP.typedGet(albumUrl, Image[].class);
    }

    public Image getImage(String albumId, String imageId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "image", imageId);

        return getImgur().HTTP.typedGet(albumUrl, Image.class);
    }

    public AlbumIds createAlbum(AlbumCreationDetails details) throws ApiRequestException {
        return getImgur().HTTP.typedPost(getEndpointUrl(), AlbumIds.class, details.getAsParams(), true);
    }

    public void updateAlbum(String albumId, AlbumCreationDetails details) throws ApiRequestException {
        String updateUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().HTTP.post(updateUrl, details.getAsParams(), true);
    }

//    TODO create anonymous
//    TODO update anonymous


    public void deleteAlbum(String albumId) throws ApiRequestException {
        String albumUrl = endpointUrlWithSinglePathParameter(albumId);

        getImgur().HTTP.delete(albumUrl);
    }

    public void favourite(String albumId) throws ApiRequestException {
        String albumUrl = endpointUrlWithMultiplePathParameters(albumId, "favorite");

        getImgur().HTTP.post(albumUrl, true);
    }

    public void setImages(String albumId, String[] imageIds) throws ApiRequestException {
        String setUrl = endpointUrlWithSinglePathParameter(albumId);

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().HTTP.post(setUrl, params, true);
    }

//    TODO Set Album Images anonymous

    public void addImages(String albumId, String[] imageIds) throws ApiRequestException {
        String addUrl = endpointUrlWithMultiplePathParameters(albumId, "add");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().HTTP.post(addUrl, params, true);
    }

    public void removeImages(String albumId, String[] imageIds) throws ApiRequestException {
        String addUrl = endpointUrlWithMultiplePathParameters(albumId, "remove_images");

        List<NameValuePair> params = getParamsFor("ids", Utils.asCommaSeparatedList(imageIds));

        getImgur().HTTP.delete(addUrl, params, true);
    }

//    TODO Add Images to an Album anonymous
//    TODO Remove Images from an Album anonymous
}



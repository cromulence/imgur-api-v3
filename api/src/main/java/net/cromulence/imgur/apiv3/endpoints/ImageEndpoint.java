package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.endpoints.params.ImageUploadDetails;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ImageEndpoint extends AbstractEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ImageEndpoint.class);

    private static final String ENDPOINT_NAME = "image";

    public ImageEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Image getImage(String imageId) throws ApiRequestException {
        String imageUrl = endpointUrlWithSinglePathParameter(imageId);

        return getImgur().HTTP.typedGet(imageUrl, Image.class);
    }

    public Image uploadImage(ImageUploadDetails details) throws ApiRequestException {
        return uploadImage(details, true);
    }

    public Image uploadAnonymousImage(ImageUploadDetails details) throws ApiRequestException {
        return uploadImage(details, false);
    }

    private Image uploadImage(ImageUploadDetails details, boolean authenticated) throws ApiRequestException {

        ArrayList<NameValuePair> postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("image", details.getImageData()));
        postParameters.add(new BasicNameValuePair("type", details.getUploadType()));

        if (details.getTitle() != null) {
            postParameters.add(new BasicNameValuePair("title", details.getTitle()));
        }

        if (details.getDescription() != null) {
            postParameters.add(new BasicNameValuePair("description", details.getDescription()));
        }

        if (details.getTitle() != null) {
            postParameters.add(new BasicNameValuePair("name", details.getName()));
        }

        return getImgur().HTTP.typedPost(getEndpointUrl(), Image.class, postParameters, authenticated);
    }

    public void deleteImage(String imageId) throws ApiRequestException {
        String url = getEndpointUrl() + "/" + imageId;

        getImgur().HTTP.delete(url, true);
    }

    public void deleteAnonymousImage(String deleteHash) throws ApiRequestException {
        String url = getEndpointUrl() + "/" + deleteHash;

        getImgur().HTTP.delete(url, false);
    }

    public void updateImageInfo(String imageId, String title, String description) throws ApiRequestException {
        doUpdateImageInfo(imageId, title, description, true);
    }

    public void updateAnonymousImageInfo(String imageId, String title, String description) throws ApiRequestException {
        doUpdateImageInfo(imageId, title, description, false);
    }

    private void doUpdateImageInfo(String imageId, String title, String description, boolean authenticated) throws ApiRequestException {
        String url = endpointUrlWithSinglePathParameter(imageId);

        List<NameValuePair> postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("title", title));
        postParameters.add(new BasicNameValuePair("description", description));

        getImgur().HTTP.post(url, postParameters, authenticated);
    }

    public void favoriteImage(String imageId) throws ApiRequestException {
        String url = String.format("%s/%s/favorite", getEndpointUrl(), imageId);

        getImgur().HTTP.post(url);
    }


}

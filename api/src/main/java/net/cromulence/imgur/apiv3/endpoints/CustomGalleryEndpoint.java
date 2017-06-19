package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.Utils;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.CustomGallery;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.constants.GallerySort;
import net.cromulence.imgur.apiv3.datamodel.constants.GalleryWindow;

public class CustomGalleryEndpoint extends AbstractEndpoint {

    private static final String CUSTOM = "custom";

    public CustomGalleryEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "g";
    }

    public Paginated<CustomGallery> getCustomGallery() throws ApiRequestException {
        return getCustomGallery(GallerySort.VIRAL, GalleryWindow.WEEK);
    }

    public Paginated<CustomGallery> getCustomGallery(GallerySort sort, GalleryWindow window) throws ApiRequestException {
        return new Paginated<>((int page) -> getCustomGallery(sort, window, page));
    }

    public CustomGallery getCustomGallery(GallerySort sort, GalleryWindow window, int page) throws ApiRequestException {
        String customGalleryUrl = endpointUrlWithMultiplePathParameters(CUSTOM, sort.toString(), window.toString(), page(page));

        return getImgur().http.typedGet(customGalleryUrl, CustomGallery.class);
    }

    public GalleryEntry getEntry(String entryId) throws ApiRequestException {
        String customGalleryUrl = endpointUrlWithMultiplePathParameters(CUSTOM, entryId);

        return getImgur().http.typedGet(customGalleryUrl, GalleryEntry.class);
    }

    public void addTags(String[] tags) throws ApiRequestException {
        String customGalleryUrl = endpointUrlWithMultiplePathParameters(CUSTOM, "add_tags");

        getImgur().http.put(customGalleryUrl, getParamsFor("tags", Utils.asCommaSeparatedList(tags)));
    }

    public void removeTags(String[] tags) throws ApiRequestException {
        String customGalleryUrl = endpointUrlWithMultiplePathParameters(CUSTOM, "add_tags");

        getImgur().http.delete(customGalleryUrl, getParamsFor("tags", Utils.asCommaSeparatedList(tags)));
    }
}

package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Tag;

public class TagsEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "tags";

    public TagsEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Tag[] getDefaultTags() throws ApiRequestException {
        final String url = getEndpointUrl();

        return getImgur().http.typedGet(url, Tag[].class);
    }
}

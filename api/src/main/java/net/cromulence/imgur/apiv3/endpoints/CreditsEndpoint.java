package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Credits;

public class CreditsEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "credits";

    private Credits credits = null;

    public CreditsEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Credits getCredits() throws ApiRequestException {

        // TODO explain why credits are a mess

        final Credits newCredits = getImgur().http.typedGet(getEndpointUrl(), Credits.class, true);

        if(credits == null) {
            credits = newCredits;
        } else {
            update(newCredits);
        }

        return credits;
    }

    public void update(Credits newCredits) {

        // If the response has all of the data, just use it
        if(newCredits.isFullyPopulated()) {
            credits = newCredits;
        }

        // If it is a partial update, we need to ensure we have something for it to update
        if (credits != null) {
            newCredits.mergeIn(credits);
            credits = newCredits;
        }

        // else ignore it. getCredits will have to go to the API
    }
}

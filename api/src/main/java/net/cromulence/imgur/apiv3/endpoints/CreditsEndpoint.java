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

        // This probably seems far more complicated than it needs to be, and it is. There are 3 different types of
        // credits, and 2 different sources of credit info, which give different subsets of the credit info.
        //
        // * User credits are the limits for the logged in account. These limits are affected by use across all clients.
        //   Loading images and reading comments uses these credits
        // * App credits are the limits for the Imgur client in use. These limits are affected by all users of a client.
        //   These are used for the same things as User credits
        // * POST credits are the limits for the logged in account. These limits are affected by use across all clients.
        //   Uploading images and posting uses these credits
        //
        // When you use the credits endpoint, you get an object back which contains User and App credits. When you make
        // other requests (non-POST), the response includes headers with User and App credit info. When you make
        // a POST request, the response includes headers detailing the user's POST credits.
        //
        // This means that any given response will only contain a subset of the credits info. User and App credits will
        // be updated frequently (from the headers, by ImgurApiLimitLoggingInsepector), and can also be explicitly
        // requested at any time. POST credits are only updated when the user makes a POST request, again from the
        // headers

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

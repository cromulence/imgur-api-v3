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
        return getCredits(false);
    }

    public Credits getCredits(boolean forceServerRequest) throws ApiRequestException {

        if(forceServerRequest || credits == null) {
            final Credits newCredits = getImgur().HTTP.typedGet(getEndpointUrl(), Credits.class, true);

            if(credits == null) {
                credits = newCredits;
            } else {
                credits.setUserLimit(newCredits.getUserLimit());
                credits.setUserRemaining(newCredits.getUserRemaining());
                credits.setUserReset(newCredits.getUserReset());
                credits.setClientLimit(newCredits.getClientLimit());
                credits.setClientRemaining(newCredits.getClientRemaining());
            }
        }

        return credits;
    }

    public void setCreditsFromResponse(Credits newCredits) {
        credits = newCredits;
    }
}

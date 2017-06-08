package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.ApiResponse;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.AppCredits;
import net.cromulence.imgur.apiv3.datamodel.Credits;

import org.apache.http.Header;

public class CreditsEndpoint extends AbstractEndpoint {

    private static final String ENDPOINT_NAME = "credits";

    private static final String PREFIX = "X-Post-Rate-Limit-";

    private static final String HEADER_LIMIT = PREFIX + "Limit";
    private static final String HEADER_REMAINING = PREFIX + "Remaining";
    private static final String HEADER_RESET = PREFIX + "Reset";

    public CreditsEndpoint(Imgur imgur) {
        super(imgur);
    }

    public String getEndpointName() {
        return ENDPOINT_NAME;
    }

    public Credits getCredits() throws ApiRequestException {
        return getImgur().HTTP.typedGet(getEndpointUrl(), Credits.class, true);
    }

    public AppCredits getAppCredits() throws ApiRequestException {
        final ApiResponse response;

        response = getImgur().HTTP.rawGet(getEndpointUrl(), true);

        Header limitHeader = response.getResponse().getFirstHeader(HEADER_LIMIT);
        Header remainHeader = response.getResponse().getFirstHeader(HEADER_REMAINING);
        Header resetHeader = response.getResponse().getFirstHeader(HEADER_RESET);

        Integer limit = -1;
        Integer remain = -1;
        Integer reset = -1;

        if (limitHeader != null) limit = Integer.parseInt(limitHeader.getValue());
        if (remainHeader != null) remain = Integer.parseInt(remainHeader.getValue());
        if (resetHeader != null) reset = Integer.parseInt(resetHeader.getValue());

        return new AppCredits(limit, remain, reset);
    }
}

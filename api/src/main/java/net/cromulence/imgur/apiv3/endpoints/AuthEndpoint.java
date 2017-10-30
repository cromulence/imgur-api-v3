package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.AuthResponse;
import net.cromulence.imgur.apiv3.datamodel.constants.AuthResponseType;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthEndpoint extends AbstractEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(AuthEndpoint.class);

    public AuthEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "oauth2";
    }

    @Override
    public String getEndpointUrl() {
        // The OAuth endpoint isn't part of the v3 URL space
        return String.format("%s/%s", BASE_API_URL, getEndpointName());
    }

    private List<NameValuePair> authParams(AuthResponseType grantType, String name, String value) {
        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("client_id", getImgur().data.getClientId()));
        params.add(new BasicNameValuePair("client_secret", getImgur().data.getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", grantType.toString()));
        params.add(new BasicNameValuePair(name, value));

        return params;
    }

    public void exchangePin(String pin, ImgurOAuthHandler ah) throws IOException, ApiRequestException {
        final String url = endpointUrlWithSinglePathParameter("token");

        final AuthResponse auth = getImgur().http.initialAuth(url, authParams(AuthResponseType.PIN, "pin", pin));

        LOG.debug("exchangePin: got auth response accessToken[" + auth.getAccessToken() + "] refreshToken[" + auth.getRefreshToken() + "] expiresIn[" + auth.getExpiresIn() + "]");

        ah.handleAuth(auth);
    }

    public boolean exchangeCode(String code) throws IOException, ApiRequestException {
        return doToken(authParams(AuthResponseType.AUTHORIZATION_CODE, "code", code));
    }

    public boolean refreshToken() throws IOException, ApiRequestException {
        return doToken(authParams(AuthResponseType.REFRESH_TOKEN, "refresh_token", getImgur().authHandler.getRefreshToken()));
    }

    private boolean doToken(List<NameValuePair> params) throws IOException, ApiRequestException {
        final String refreshUrl = endpointUrlWithSinglePathParameter("token");

        final AuthResponse auth = getImgur().http.auth(refreshUrl, params, true);

        LOG.debug("doToken: got auth response accessToken[{}] refreshToken[{}] expiresIn[{}]",
            auth.getAccessToken(), auth.getRefreshToken(), auth.getExpiresIn());

        getImgur().authHandler.handleAuth(auth);

        return true;
    }
}

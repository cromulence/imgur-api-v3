package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.AuthResponse;

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

    private List<NameValuePair> authParams(String grantType, String name, String value) {
        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        params.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", grantType));
        params.add(new BasicNameValuePair(name, value));

        return params;
    }

    public void exchangePin(String pin, ImgurOAuthHandler ah) throws IOException, ApiRequestException {
        final String url = endpointUrlWithSinglePathParameter("token");

        final AuthResponse auth = getImgur().HTTP.initialAuth(url, authParams("pin", "pin", pin));

        LOG.debug("exchangePin: got auth response accessToken[" + auth.getAccessToken() + "] refreshToken[" + auth.getRefreshToken() + "] expiresIn[" + auth.getExpiresIn() + "]");

        ah.handleAuth(auth);
    }

    public boolean exchangeCode(String code) throws IOException, ApiRequestException {
        return doToken(authParams("authorization_code", "code", code));
    }

    public boolean refreshToken() throws IOException, ApiRequestException {
        return doToken(authParams("refresh_token", "refresh_token", getImgur().AUTH_HANDLER.getRefreshToken()));
    }

    protected boolean doToken(List<NameValuePair> params) throws IOException, ApiRequestException {
        final String refreshUrl = endpointUrlWithSinglePathParameter("token");

        final AuthResponse auth = getImgur().HTTP.auth(refreshUrl, params, true);

        LOG.debug("doToken: got auth response accessToken[" + auth.getAccessToken() + "] refreshToken[" + auth.getRefreshToken() + "] expiresIn[" + auth.getExpiresIn() + "]");

        getImgur().AUTH_HANDLER.handleAuth(auth);

        return true;
    }
}

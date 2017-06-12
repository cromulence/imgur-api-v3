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

    public AuthEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "oauth2";
    }

    private static final Logger LOG = LoggerFactory.getLogger(AuthEndpoint.class);

    public void exchangePin(String pin, ImgurOAuthHandler ah) throws IOException, ApiRequestException {
        final String url = endpointUrlWithSinglePathParameter("token");

        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        params.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", "pin"));
        params.add(new BasicNameValuePair("pin", pin));

        final AuthResponse auth = getImgur().HTTP.initialAuth(url, params);

        LOG.debug("exchangePin: got auth response accessToken[" + auth.getAccessToken() + "] refreshToken[" + auth.getRefreshToken() + "] expiresIn[" + auth.getExpiresIn() + "]");

        ah.handleAuth(auth);
    }

    public boolean exchangeCode(String code) throws IOException, ApiRequestException {
        final ArrayList<NameValuePair> postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        postParameters.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        postParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParameters.add(new BasicNameValuePair("code", code));

        return doToken(postParameters);
    }

    public boolean refreshToken() throws IOException, ApiRequestException {
        final ArrayList<NameValuePair> postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        postParameters.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        postParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        postParameters.add(new BasicNameValuePair("refresh_token", getImgur().AUTH_HANDLER.getRefreshToken()));

        return doToken(postParameters);
    }

    protected boolean doToken(ArrayList<NameValuePair> params) throws IOException, ApiRequestException {
        final String refreshUrl = endpointUrlWithSinglePathParameter("token");

        final AuthResponse auth = getImgur().HTTP.auth(refreshUrl, params, true);

        LOG.debug("doToken: got auth response accessToken[" + auth.getAccessToken() + "] refreshToken[" + auth.getRefreshToken() + "] expiresIn[" + auth.getExpiresIn() + "]");

        getImgur().AUTH_HANDLER.handleAuth(auth);

        return true;
    }
}

package net.cromulence.imgur.apiv3.auth;

import com.google.gson.Gson;

import net.cromulence.imgur.apiv3.api.ApiResponse;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.AuthResponse;
import net.cromulence.imgur.apiv3.endpoints.AbstractEndpoint;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthUtils extends AbstractEndpoint {

    public AuthUtils(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "oauth2";
    }

    private static final Logger LOG = LoggerFactory.getLogger(AuthUtils.class);

    public void exchangePin(String pin, ImgurOAuthHandler ah) throws IOException, ApiRequestException {
        String url = "https://api.imgur.com/oauth2/token";

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        params.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        params.add(new BasicNameValuePair("grant_type", "pin"));
        params.add(new BasicNameValuePair("pin", pin));

        ApiResponse doPost = getImgur().HTTP.initialAuth(url, params);

        AuthResponse auth = new Gson().fromJson(doPost.getResponseBody(), AuthResponse.class);

        LOG.debug("exchangePin: got auth response accessToken[" + auth.getAccess_token() + "] refreshToken[" + auth.getRefresh_token() + "] expiresIn[" + auth.getExpires_in() + "]");

        ah.handleAuth(auth);
    }

    public boolean exchangeCode(String code) throws IOException, ApiRequestException {
        ArrayList<NameValuePair> postParameters;

        postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        postParameters.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        postParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParameters.add(new BasicNameValuePair("code", code));

        return doToken(postParameters);
    }

    public boolean refreshToken() throws IOException, ApiRequestException {
        ArrayList<NameValuePair> postParameters;

        postParameters = new ArrayList<>();

        postParameters.add(new BasicNameValuePair("client_id", getImgur().DATA.getClientId()));
        postParameters.add(new BasicNameValuePair("client_secret", getImgur().DATA.getClientSecret()));
        postParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        postParameters.add(new BasicNameValuePair("refresh_token", getImgur().AUTH.getRefreshToken()));

        return doToken(postParameters);
    }

    protected boolean doToken(ArrayList<NameValuePair> params) throws IOException, ApiRequestException {
        LOG.debug("doToken: in params[" + params + "]");

        String refreshUrl = "https://api.imgur.com/oauth2/token";

        ApiResponse doPost = getImgur().HTTP.auth(refreshUrl, params, true);

        AuthResponse auth = new Gson().fromJson(doPost.getResponseBody(), AuthResponse.class);

        LOG.debug("doToken: got auth response accessToken[" + auth.getAccess_token() + "] refreshToken[" + auth.getRefresh_token() + "] expiresIn[" + auth.getExpires_in() + "]");

        getImgur().AUTH.handleAuth(auth);

        return true;
    }
}

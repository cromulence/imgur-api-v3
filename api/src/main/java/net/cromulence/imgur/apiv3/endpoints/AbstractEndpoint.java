package net.cromulence.imgur.apiv3.endpoints;

import com.google.common.base.Strings;

import net.cromulence.imgur.apiv3.api.Imgur;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEndpoint {

    protected static final String BASE_API_URL = "https://api.imgur.com";
    private static final String API_VERSION = "3";

    private static final String BASE_V3_API_URL = String.format("%s/%s", BASE_API_URL, API_VERSION);

    private final Imgur imgur;

    public AbstractEndpoint(Imgur imgur) {
        this.imgur = imgur;
    }

    protected Imgur getImgur() {
        return imgur;
    }

    public String getEndpointUrl() {
        return String.format("%s/%s", BASE_V3_API_URL, getEndpointName());
    }

    String endpointUrlWithSinglePathParameter(String parameter) {
        return endpointUrlWithMultiplePathParameters(parameter);
    }

    String endpointUrlWithSinglePathParameter(Number parameter) {
        return endpointUrlWithMultiplePathParameters(Integer.toString(parameter.intValue()));
    }

    String endpointUrlWithMultiplePathParameters(String... parameters) {
        return urlFromParts(getEndpointUrl(), parameters);
    }

    String baseUrlWithMultiplePathParameters(String... parameters) {
        return urlFromParts(BASE_V3_API_URL, parameters);
    }

    private String urlFromParts(String base, String... parameters) {
        StringBuilder sb = new StringBuilder();

        for (String p : parameters) {
            if (!Strings.isNullOrEmpty(p)) {
                sb.append("/").append(p);
            }
        }

        return String.format("%s%s", base, sb.toString());
    }

    protected abstract String getEndpointName();

    protected String page(int page) {
        if (page > -1) {
            return Integer.toString(page);
        }

        return null;
    }

    protected List<NameValuePair> getParamsFor(String name, String value) {
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair(name, value));

        return params;
    }
}

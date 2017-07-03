package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;

import org.apache.http.HttpResponse;

import java.io.Serializable;

/**
 * Encapsulated HTTP response
 */
public class ApiResponse implements Serializable {
    private final transient HttpResponse response;
    private String responseBody;

    public ApiResponse(HttpResponse response) throws ApiRequestException {
        this.response = response;

        try {
            this.responseBody = HttpUtils.readStream(response.getEntity().getContent());
        } catch (Exception e) {
            throw new ApiRequestException("Unable to read content from response", e);
        }
    }

    public String getReasonPhrase() {
        return response.getStatusLine().getReasonPhrase();
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public HttpResponse getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return getStatusCode() + " " + getReasonPhrase() + "\nResponse Object:\n" + getResponse() + "\nResponse body\n" + getResponseBody();
    }

    public boolean isSuccess() {
        return getStatusCode() >= 200 && getStatusCode() <= 299;
    }
}

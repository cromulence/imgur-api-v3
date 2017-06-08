package net.cromulence.imgur.apiv3.api.exceptions;

import net.cromulence.imgur.apiv3.api.ApiResponse;

/**
 * * Exceptions used to indicate an error on the server side (5xx)
 */
public class ImgurServerException extends ApiRequestException {
    private final ApiResponse apiResponse;

    public ImgurServerException(ApiResponse apiResponse) {
        super(apiResponse.getReasonPhrase());
        this.apiResponse = apiResponse;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}

package net.cromulence.imgur.apiv3.api;

import java.io.Serializable;

/**
 * Imgur API response. Contains status information, and optionally a typed data value.
 * @param <T> The type of the object returned in the responase, if any
 */
public class BasicResponse<T> implements Serializable {

    // Any object returned in the response
    private T data;

    // Was the request successful?
    private boolean success;

    // HTTP status code
    private int status;

    // The response which this was generated from
    private ApiResponse rawApiResponse;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRawApiResponse(ApiResponse rawApiResponse) {
        this.rawApiResponse = rawApiResponse;
    }

    public ApiResponse getRawApiResponse() {
        return rawApiResponse;
    }
}

package net.cromulence.imgur.apiv3.api.exceptions;

import net.cromulence.imgur.apiv3.datamodel.ApiError;

public class ErrorResponseException extends ImgurRequestException {

    /** Serial ID */
    private static final long serialVersionUID = -1280491071703899275L;

    private final ApiError error;
    private final int status;

    public ErrorResponseException(int status, ApiError error) {
        super(status + " : " + error.getMethod() + " : " + error.getRequest() + " : " + error.getErrorDetails());

        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public ApiError getError() {
        return error;
    }

}

package net.cromulence.imgur.apiv3.api.exceptions;

public class ApiRequestException extends ImgurException {

    /** Serial ID */
    private static final long serialVersionUID = -537150956259035801L;

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(Throwable cause) {
        super(cause);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

package net.cromulence.imgur.apiv3.api.exceptions;

public class ImgurApiTimeoutException extends ApiRequestException {

    public ImgurApiTimeoutException(String message) {
        super(message);
    }

    public ImgurApiTimeoutException(Throwable cause) {
        super(cause);
    }

    public ImgurApiTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}

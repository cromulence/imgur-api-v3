package net.cromulence.imgur.apiv3.api.exceptions;

/**
 * Exceptions used to indicate an error in the request from the client (4xx)
 */
public class ImgurRequestException extends ApiRequestException {

    public ImgurRequestException(String message) {
        super(message);
    }

    public ImgurRequestException(Throwable cause) {
        super(cause);
    }

    public ImgurRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

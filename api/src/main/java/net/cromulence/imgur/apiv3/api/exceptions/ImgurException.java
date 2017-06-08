package net.cromulence.imgur.apiv3.api.exceptions;

/**
 *
 */
public class ImgurException extends Exception {

    public ImgurException(String message) {
        super(message);
    }

    public ImgurException(Throwable cause) {
        super(cause);
    }

    public ImgurException(String message, Throwable cause) {
        super(message, cause);
    }
}

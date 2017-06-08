package net.cromulence.imgur.apiv3.api.exceptions;

public class ResponseTypeException extends ApiRequestException {

    public ResponseTypeException(String message) {
        super(message);
    }

    public ResponseTypeException(Throwable cause) {
        super(cause);
    }

    public ResponseTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}

package net.cromulence.imgur.apiv3.api.exceptions;

public class NotAuthorizedException extends ApiRequestException {
    public NotAuthorizedException(String message) {
        super(message);
    }
}

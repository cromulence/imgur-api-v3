package net.cromulence.imgur.apiv3.api.exceptions;

public class NotFoundException extends ApiRequestException {

    public NotFoundException(String message) {
        super(message);
    }
}

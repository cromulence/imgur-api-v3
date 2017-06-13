package net.cromulence.imgur.apiv3.api.exceptions;

public class RateLimitedException extends ApiRequestException {

    public RateLimitedException(String message) {
        super(message);
    }
}
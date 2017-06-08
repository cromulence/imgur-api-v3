package net.cromulence.imgur.apiv3.api.exceptions;

public class EmailAlreadyVerifiedException extends ApiRequestException {

    public EmailAlreadyVerifiedException() {
        super("email already verified");
    }
}

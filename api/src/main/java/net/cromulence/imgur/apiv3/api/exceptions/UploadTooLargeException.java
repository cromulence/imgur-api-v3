package net.cromulence.imgur.apiv3.api.exceptions;

/**
 * Thrown if the API reports the client attempted to upload an image larger than the maximum permitted
 */
public class UploadTooLargeException extends ImgurRequestException {

    public UploadTooLargeException() {
        super("Uploaded file is larger than the supported maximum");
    }
}

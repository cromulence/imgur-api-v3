package net.cromulence.imgur.apiv3.endpoints.params;

import com.google.common.io.ByteStreams;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUploadDetailsBuilder implements ParameterObjectBuilder<ImageUploadDetails> {

    private final File fileImageToUpload;
    private final InputStream streamImageToUpload;
    private final String stringImageToUpload;

    private String title;
    private String description;
    private String name;

    public ImageUploadDetailsBuilder(File imageToUpload) {
        this(imageToUpload, null, null);
    }

    public ImageUploadDetailsBuilder(InputStream imageToUpload) {
        this(null, imageToUpload, null);
    }

    public ImageUploadDetailsBuilder(String imageToUpload) {
        this(null, null, imageToUpload);
    }

    private ImageUploadDetailsBuilder(File fileImageToUpload, InputStream streamImageToUpload, String stringImageToUpload) {
        this.fileImageToUpload = fileImageToUpload;
        this.streamImageToUpload = streamImageToUpload;
        this.stringImageToUpload = stringImageToUpload;
    }

    public ImageUploadDetailsBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ImageUploadDetailsBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ImageUploadDetailsBuilder name(String name) {
        this.name = name;
        return this;
    }


    @Override
    public ImageUploadDetails build() {
        if (fileImageToUpload != null) {
            return new ImageFileUploadDetails(title, description, name, fileImageToUpload);
        } else if (streamImageToUpload != null) {
            return new ImageStreamUploadDetails(title, description, name, streamImageToUpload);
        } else {
            return new ImageUrlUploadDetails(title, description, name, stringImageToUpload);
        }
    }

    public static class ImageStreamUploadDetails extends ImageUploadDetails {

        private final InputStream stream;

        ImageStreamUploadDetails(String title, String description, String name, InputStream stream) {
            super(title, description, name);
            this.stream = stream;
        }

        public String getImageData() throws ApiRequestException {
            byte[] bytes;
            try {
                bytes = ByteStreams.toByteArray(stream);
            } catch (IOException e) {
                throw new ApiRequestException("Unable to read image data", e);
            }

            return Base64.getEncoder().encodeToString(bytes);
        }

        public String getUploadType() {
            return "base64";
        }
    }

    public static class ImageFileUploadDetails extends ImageUploadDetails {

        private final File file;

        ImageFileUploadDetails(String title, String description, String name, File file) {
            super(title, description, name);
            this.file = file;
        }

        public String getName() {
            if (super.getName() == null) {
                return file.getName();
            }

            return super.getName();
        }

        public String getImageData() throws ApiRequestException {
            byte[] bytes;
            try {
                bytes = ByteStreams.toByteArray(new FileInputStream(file));
            } catch (IOException e) {
                throw new ApiRequestException("Unable to read image data", e);
            }

            return Base64.getEncoder().encodeToString(bytes);
        }

        public String getUploadType() {
            return "base64";
        }
    }

    public static class ImageUrlUploadDetails extends ImageUploadDetails {

        private final String url;

        ImageUrlUploadDetails(String title, String description, String name, String url) {
            super(title, description, name);
            this.url = url;
        }

        public String getImageData() {
            return url;
        }

        public String getUploadType() {
            return "url";
        }
    }
}

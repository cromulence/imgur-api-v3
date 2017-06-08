package net.cromulence.imgur.apiv3.endpoints.params;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;

public abstract class ImageUploadDetails implements ParameterObject<ImageUploadDetailsBuilder> {

    private final String title;
    private final String description;
    private final String name;

    ImageUploadDetails(String title, String description, String name) {
        this.title = title;
        this.description = description;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public abstract String getImageData() throws ApiRequestException;

    public abstract String getUploadType();

//    @Override
//    public ImageUploadDetailsBuilder getBuilder() {
//        return new ImageUploadDetailsBuilder();
//    }
}

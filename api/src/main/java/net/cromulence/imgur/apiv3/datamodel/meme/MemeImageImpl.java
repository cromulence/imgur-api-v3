package net.cromulence.imgur.apiv3.datamodel.meme;

import net.cromulence.imgur.apiv3.datamodel.GalleryDetails;
import net.cromulence.imgur.apiv3.datamodel.GalleryImageImpl;
import net.cromulence.imgur.apiv3.datamodel.Image;

public class MemeImageImpl extends GalleryImageImpl implements MemeImage {
    private final MemeMetadata metadata;

    public MemeImageImpl(Image image, GalleryDetails details, MemeMetadata metadata) {
        super(image, details);

        this.metadata = metadata;
    }

    public MemeMetadata getMetadata() {
        return metadata;
    }
}

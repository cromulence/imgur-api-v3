package net.cromulence.imgur.apiv3.datamodel.meme;

import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.GalleryAlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.GalleryDetails;

public class MemeAlbumImpl extends GalleryAlbumImpl implements MemeAlbum {
    private final MemeMetadata metadata;

    public MemeAlbumImpl(Album album, GalleryDetails details, MemeMetadata metadata) {
        super(album, details);

        this.metadata = metadata;
    }
}

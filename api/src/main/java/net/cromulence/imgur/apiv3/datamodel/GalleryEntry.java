package net.cromulence.imgur.apiv3.datamodel;

public interface GalleryEntry<T extends Uploaded> extends GalleryDetails, Uploaded {
    T get();
}

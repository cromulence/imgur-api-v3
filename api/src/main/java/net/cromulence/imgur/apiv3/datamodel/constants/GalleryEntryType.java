package net.cromulence.imgur.apiv3.datamodel.constants;

public enum GalleryEntryType {
    ALBUM,
    IMAGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();

    }
}

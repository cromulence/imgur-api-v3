package net.cromulence.imgur.apiv3.datamodel.constants;

public enum AlbumPrivacy {
    PUBLIC,
    HIDDEN,
    SECRET;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

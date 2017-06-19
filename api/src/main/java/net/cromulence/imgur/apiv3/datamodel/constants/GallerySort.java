package net.cromulence.imgur.apiv3.datamodel.constants;

public enum GallerySort {
    VIRAL,
    TOP,
    TIME,
    RISING;

    @Override
    public String toString() {
        return super.toString().toLowerCase();

    }
}
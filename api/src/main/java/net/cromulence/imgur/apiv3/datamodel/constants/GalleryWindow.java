package net.cromulence.imgur.apiv3.datamodel.constants;

public enum GalleryWindow {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    ALL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

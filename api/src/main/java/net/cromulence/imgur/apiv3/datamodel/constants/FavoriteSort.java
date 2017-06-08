package net.cromulence.imgur.apiv3.datamodel.constants;

public enum FavoriteSort {
    NEWEST,
    OLDEST;

    public String toString() {
        return super.toString().toLowerCase();
    }
}

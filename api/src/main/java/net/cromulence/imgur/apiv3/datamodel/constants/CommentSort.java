package net.cromulence.imgur.apiv3.datamodel.constants;

public enum CommentSort {
    BEST,
    TOP,
    NEW;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

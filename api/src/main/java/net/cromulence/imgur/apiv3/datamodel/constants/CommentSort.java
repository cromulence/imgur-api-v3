package net.cromulence.imgur.apiv3.datamodel.constants;

public enum CommentSort {
    BEST,
    TOP,
    NEW;

    public String toString() {
        return super.toString().toLowerCase();
    }

    ;
}

package net.cromulence.imgur.apiv3.datamodel.constants;

public enum AccountCommentSort {
    NEWEST,
    OLDEST,
    BEST,
    WORST;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

package net.cromulence.imgur.apiv3.datamodel.constants;


public enum SubredditSort {
    TOP,
    TIME;

    @Override
    public String toString() {
        return super.toString().toLowerCase();

    }
}

package net.cromulence.imgur.apiv3.datamodel.constants;

public enum TopicSort {
    VIRAL,
    TOP,
    TIME;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

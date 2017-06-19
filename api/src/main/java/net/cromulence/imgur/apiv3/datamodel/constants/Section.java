package net.cromulence.imgur.apiv3.datamodel.constants;

public enum Section {
    HOT,
    TOP,
    USER;

    @Override
    public String toString() {
        return super.toString().toLowerCase();

    }
}

package net.cromulence.imgur.apiv3.datamodel.constants;

import com.google.gson.annotations.SerializedName;

public enum AlbumLayout {
    @SerializedName("blog")
    BLOG,
    @SerializedName("grid")
    GRID,
    @SerializedName("horizontal")
    HORIZONTAL,
    @SerializedName("vertical")
    VERTICAL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

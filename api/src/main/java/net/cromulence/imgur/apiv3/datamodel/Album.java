package net.cromulence.imgur.apiv3.datamodel;

import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;

public interface Album extends Uploaded {

    Long getAccountId();

    String getAccountUrl();

    String getCover();

    int getCoverHeight();

    int getCoverWidth();

    Image[] getImages();

    int getImagesCount();

    /**
     * The view layout of the album.
     */
    AlbumLayout getLayout();

    /**
     * Order number of the album on the user's album page (defaults to 0 if their albums haven't been reordered)
     */
    int getOrder();

    String getPrivacy();

}

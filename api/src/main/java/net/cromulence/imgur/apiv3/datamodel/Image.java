package net.cromulence.imgur.apiv3.datamodel;

public interface Image extends Uploaded {

    // http://api.imgur.com/models/image

    boolean isAnimated();

    long getBandwidth();

    // Optional
    String getGifv();

    int getHeight();

    // Optional
    boolean isLooping();

    // Optional
    String getMp4();

    // Optional
    int getMp4Size();

    String getName();

    int getSize();

    String getType();

    String getVote();

    int getWidth();
}

package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;

public class CommentResponseData implements Serializable {
    long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}

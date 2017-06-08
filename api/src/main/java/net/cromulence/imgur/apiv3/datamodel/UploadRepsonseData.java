package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;

public class UploadRepsonseData implements Serializable {
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;

public class Data<T extends Object> implements Serializable {
    private T[] data;

    public void setData(T[] data) {
        this.data = data;
    }

    public T[] getData() {
        return data;
    }

}

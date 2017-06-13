package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Credits implements Serializable {

    private int postLimit;
    private int postRemaining;
    private long postReset;

    @SerializedName("UserLimit")
    private int userLimit;

    @SerializedName("UserRemaining")
    private int userRemaining;

    @SerializedName("UserReset")
    private long userReset;

    @SerializedName("ClientLimit")
    private int clientLimit;

    @SerializedName("ClientRemaining")
    private int clientRemaining;

    public int getPostLimit() {
        return postLimit;
    }

    public void setPostLimit(int postLimit) {
        this.postLimit = postLimit;
    }

    public int getPostRemaining() {
        return postRemaining;
    }

    public void setPostRemaining(int postRemaining) {
        this.postRemaining = postRemaining;
    }

    public long getPostReset() {
        return postReset;
    }

    public void setPostReset(long postReset) {
        this.postReset = postReset;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(int userLimit) {
        userLimit = userLimit;
    }

    public int getUserRemaining() {
        return userRemaining;
    }

    public void setUserRemaining(int userRemaining) {
        userRemaining = userRemaining;
    }

    public long getUserReset() {
        return userReset;
    }

    public void setUserReset(long userReset) {
        userReset = userReset;
    }

    public int getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(int clientLimit) {
        clientLimit = clientLimit;
    }

    public int getClientRemaining() {
        return clientRemaining;
    }

    public void setClientRemaining(int clientRemaining) {
        clientRemaining = clientRemaining;
    }

    // {"data":{"UserLimit":500,"UserRemaining":500,"UserReset":1464006639,"ClientLimit":12500,"ClientRemaining":12500},"success":true,"status":200}
}

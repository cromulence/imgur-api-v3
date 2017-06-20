package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Credits implements Serializable {

    private int postLimit = -1;
    private int postRemaining = -1;
    private long postReset = -1;

    @SerializedName("UserLimit")
    private int userLimit = -1;

    @SerializedName("UserRemaining")
    private int userRemaining = -1;

    @SerializedName("UserReset")
    private long userReset = -1;

    @SerializedName("ClientLimit")
    private int clientLimit = -1;

    @SerializedName("ClientRemaining")
    private int clientRemaining = -1;

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
        this.userLimit = userLimit;
    }

    public int getUserRemaining() {
        return userRemaining;
    }

    public void setUserRemaining(int userRemaining) {
        this.userRemaining = userRemaining;
    }

    public long getUserReset() {
        return userReset;
    }

    public void setUserReset(long userReset) {
        this.userReset = userReset;
    }

    public int getClientLimit() {
        return clientLimit;
    }

    public void setClientLimit(int clientLimit) {
        this.clientLimit = clientLimit;
    }

    public int getClientRemaining() {
        return clientRemaining;
    }

    public void setClientRemaining(int clientRemaining) {
        this.clientRemaining = clientRemaining;
    }

    public boolean isFullyPopulated() {
        return postLimit != -1 && postRemaining != -1 && postReset != -1 && userLimit != -1 &&
            userRemaining != -1 && userReset != -1 && clientLimit != -1 && clientRemaining != -1;
    }

    public void mergeIn(Credits credits) {
        if(postLimit == -1) {
            postLimit = credits.getPostLimit();
        }

        if(postRemaining == -1) {
            postRemaining = credits.getPostRemaining();
        }

        if(postReset == -1) {
            postReset = credits.getPostReset();
        }

        if(userLimit == -1) {
            userLimit = credits.getUserLimit();
        }
        if(userRemaining == -1) {
            userRemaining = credits.getUserRemaining();
        }
        if(userReset == -1) {
            userReset = credits.getUserReset();
        }
        if(clientLimit == -1) {
            clientLimit = credits.getClientLimit();
        }
        if(clientRemaining == -1) {
            clientRemaining = credits.getClientRemaining();
        }
    }

    @Override
    public String toString() {
        return String.format("Credits{postLimit=%d, postRemaining=%d, postReset=%d, userLimit=%d, userRemaining=%d, userReset=%d, clientLimit=%d, clientRemaining=%d}", postLimit, postRemaining, postReset, userLimit, userRemaining, userReset, clientLimit, clientRemaining);
    }

// {"data":{"UserLimit":500,"UserRemaining":500,"UserReset":1464006639,"ClientLimit":12500,"ClientRemaining":12500},"success":true,"status":200}
}

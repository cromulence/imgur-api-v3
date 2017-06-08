package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;

public class Credits implements Serializable {

    private int UserLimit;
    private int UserRemaining;
    private long UserReset;

    private int ClientLimit;
    private int ClientRemaining;

    public int getUserLimit() {
        return UserLimit;
    }

    public void setUserLimit(int userLimit) {
        UserLimit = userLimit;
    }

    public int getUserRemaining() {
        return UserRemaining;
    }

    public void setUserRemaining(int userRemaining) {
        UserRemaining = userRemaining;
    }

    public long getUserReset() {
        return UserReset;
    }

    public void setUserReset(long userReset) {
        UserReset = userReset;
    }

    public int getClientLimit() {
        return ClientLimit;
    }

    public void setClientLimit(int clientLimit) {
        ClientLimit = clientLimit;
    }

    public int getClientRemaining() {
        return ClientRemaining;
    }

    public void setClientRemaining(int clientRemaining) {
        ClientRemaining = clientRemaining;
    }

    // {"data":{"UserLimit":500,"UserRemaining":500,"UserReset":1464006639,"ClientLimit":12500,"ClientRemaining":12500},"success":true,"status":200}


}

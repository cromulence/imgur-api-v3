package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;

public class AppCredits implements Serializable {

    private Integer limit;
    private Integer remain;
    private Integer reset;

    public AppCredits(Integer limit, Integer remain, Integer reset) {
        this.limit = limit;
        this.remain = remain;
        this.reset = reset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }
}

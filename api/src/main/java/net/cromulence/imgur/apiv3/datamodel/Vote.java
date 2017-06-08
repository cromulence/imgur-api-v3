package net.cromulence.imgur.apiv3.datamodel;

public class Vote {

    private int ups;// 	integer 	Number of upvotes
    private int downs;// 	integer 	Number of downvotes

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }
}

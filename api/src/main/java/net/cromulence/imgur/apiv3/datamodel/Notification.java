package net.cromulence.imgur.apiv3.datamodel;

public class Notification<N extends Notifiable> {

    /**
     * The ID for the notification
     */
    private int id;

    // account_id
    /**
     * The Account ID for the notification
     */
    private int account_id;

    /**
     * Has the user viewed the image yet?
     */
    private boolean viewed;

    /**
     * This can be any other model, currently only using comments and conversation metadata.
     */
    private N content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public N getContent() {
        return content;
    }
}

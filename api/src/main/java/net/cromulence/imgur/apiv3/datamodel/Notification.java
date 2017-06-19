package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class Notification<N extends Notifiable> {

    /**
     * The ID for the notification
     */
    private int id;

    // account_id
    /**
     * The Account ID for the notification
     */
    @SerializedName("account_id")
    private int accountId;

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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public void setContent(N content) {
        this.content = content;
    }
}

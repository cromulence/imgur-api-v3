package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Conversation implements Serializable, Notifiable {

    /** Conversation ID */
    private int id;

    // last_message_preview
    /** Preview of the last message */
    @SerializedName("last_message_preview")
    private String lastMessagePreview;

    /** Time of last sent message, epoch time */
    private int datetime;

    //with_account_id
    /** Account ID of the other user in conversation */
    @SerializedName("with_account_id")
    private int withAccountId;

    //with_account
    /** Account username of the other user in conversation */
    @SerializedName("with_account")
    private String withAccount;

    //message_count
    /** Total number of messages in the conversation */
    @SerializedName("message_count")
    private int messageCount;

    /** OPTIONAL: (only available when requesting a specific conversation) Reverse sorted such that most recent message is at the end of the array. */
    private Message[] messages;

    /** OPTIONAL: (only available when requesting a specific conversation) Flag to indicate whether you've reached the beginning of the thread. */
    private boolean done;

    /** OPTIONAL: (only available when requesting a specific conversation) Number of the next page */
    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastMessagePreview() {
        return lastMessagePreview;
    }

    public void setLastMessagePreview(String lastMessagePreview) {
        this.lastMessagePreview = lastMessagePreview;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public int getWithAccountId() {
        return withAccountId;
    }

    public void setWithAccountId(int withAccountId) {
        this.withAccountId = withAccountId;
    }

    public String getWithAccount() {
        return withAccount;
    }

    public void setWithAccount(String withAccount) {
        this.withAccount = withAccount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

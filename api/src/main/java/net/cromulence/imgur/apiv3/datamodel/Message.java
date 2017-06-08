package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class Message {

    /**
     * The ID for the message
     */
    private int id;

    /**
     * Account username of person sending the message
     */
    private String from;

    //account_id
    /**
     * The account ID of the person receiving the message
     */
    @SerializedName("account_id")
    private int accountId;

    //sender_id
    /**
     * The account ID of the person who sent the message
     */
    @SerializedName("sender_id")
    private int senderId;

    /**
     * Text of the message
     */
    private String body;

    //conversation_id
    /**
     * ID for the overall conversation
     */
    @SerializedName("conversation_id")
    private int conversationId;

    /**
     * Time message was sent, epoch time
     */
    private int datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }
}

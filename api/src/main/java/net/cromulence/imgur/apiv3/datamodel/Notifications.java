package net.cromulence.imgur.apiv3.datamodel;

public class Notifications {

    private Notification<Comment>[] replies;

    private Notification<Conversation>[] messages;

    public Notification<Comment>[] getReplies() {
        return replies;
    }

    public void setReplies(Notification[] replies) {
        this.replies = replies;
    }

    public Notification<Conversation>[] getMessages() {
        return messages;
    }

    public void setMessages(Notification[] messages) {
        this.messages = messages;
    }
}

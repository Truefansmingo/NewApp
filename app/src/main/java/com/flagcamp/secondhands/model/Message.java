package com.flagcamp.secondhands.model;

import java.util.Date;

public class Message {

    private String messageUsername;
    private String messageText;
    private String messageUserId;
    private long messageTime;

    public Message(String messageUsername, String messageText, String messageUserId) {
        this.messageUsername = messageUsername;
        this.messageText = messageText;
        this.messageUserId = messageUserId;
        this.messageTime = new Date().getTime();
    }

    public String getMessageUsername() {
        return messageUsername;
    }

    public void setMessageUsername(String messageUsername) {
        this.messageUsername = messageUsername;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}

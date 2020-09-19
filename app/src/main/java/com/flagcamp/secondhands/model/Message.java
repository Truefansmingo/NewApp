package com.flagcamp.secondhands.model;

import java.util.Date;

public class Message {

    private String messageId;
    private String messageUsername;
    private String messageText;
    private String messageUserId;
    private long messageTime;
    private String messagePhotoUrl;

    public Message(String messageId, String messageUsername, String messageText, String messageUserId, String messagePhotoUrl) {
        this.messageId = messageId;
        this.messageUsername = messageUsername;
        this.messageText = messageText;
        this.messageUserId = messageUserId;
        this.messageTime = new Date().getTime();
        this.messagePhotoUrl = messagePhotoUrl;
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

    public String getMessagePhotoUrl() {
        return messagePhotoUrl;
    }

    public void setMessagePhotoUrl(String messagePhotoUrl) {
        this.messagePhotoUrl = messagePhotoUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

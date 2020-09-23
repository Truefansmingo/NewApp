package com.flagcamp.secondhands.notification;

import com.flagcamp.secondhands.model.Message;

public class NotificationSender {
    public Message message;
    public String token;

    public NotificationSender(Message message, String token) {
        this.message = message;
        this.token = token;
    }
}

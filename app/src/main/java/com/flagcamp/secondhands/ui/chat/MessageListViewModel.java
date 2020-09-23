package com.flagcamp.secondhands.ui.chat;

import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.Message;
import com.flagcamp.secondhands.notification.NotificationRepository;

public class MessageListViewModel extends ViewModel {
    private final NotificationRepository repository;

    public MessageListViewModel(NotificationRepository repository) {
        this.repository = repository;
    }

    public void updateToken() {
        repository.updateToken();
    }

    public void sendNotifications(String token, Message message) {
        repository.sendNotifications(token, message);
    }
}

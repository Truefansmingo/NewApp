package com.flagcamp.secondhands.notification;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.secondhands.ui.chat.MessageListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final NotificationRepository repository;

    public ViewModelFactory(NotificationRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MessageListViewModel.class)) {
            return (T) new MessageListViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}

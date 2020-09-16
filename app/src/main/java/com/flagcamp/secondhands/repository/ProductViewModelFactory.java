package com.flagcamp.secondhands.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.secondhands.ui.home.HomeViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory{
    private final ProductRespository repository;

    public ProductViewModelFactory(ProductRespository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }else{
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}

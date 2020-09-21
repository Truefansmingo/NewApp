package com.flagcamp.secondhands.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.ui.fav.FavViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final ProductRepository repository;

    public ProductViewModelFactory(ProductRepository repository) {
        this.repository = repository;
    }
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom((FavViewModel.class))){
            return (T)new FavViewModel(repository);
        } else{
            throw new IllegalStateException("Unknown ViewModel");
        }
    }


}

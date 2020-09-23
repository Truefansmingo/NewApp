package com.flagcamp.secondhands.data;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.secondhands.ui.fav.FavViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final DummyData data;

    public ProductViewModelFactory(DummyData data) {
        this.data = data;
    }
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom((FavViewModel.class))){
            return (T)new FavViewModel(data);
        } else{
            throw new IllegalStateException("Unknown ViewModel");
        }
    }


}

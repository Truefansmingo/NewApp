package com.flagcamp.secondhands.repository;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.ui.fav.FavViewModel;
import com.flagcamp.secondhands.ui.map.MapViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final ProductRepository repository;

    public ProductViewModelFactory(ProductRepository repository) {
        this.repository = repository;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom((FavViewModel.class))) {
            return (T) new FavViewModel(repository);
        } else if (modelClass.isAssignableFrom((MapViewModel.class))) {
            return (T) new MapViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }

    }



}

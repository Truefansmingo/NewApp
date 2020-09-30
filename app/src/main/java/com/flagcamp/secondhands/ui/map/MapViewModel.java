package com.flagcamp.secondhands.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;

import java.util.List;

public class MapViewModel extends ViewModel {
    private final ProductRepository repository;

    public MapViewModel(ProductRepository repository) {
        this.repository = repository;
    }


    public LiveData<List<Product>> getProductGeoInfo(double lat, double lon) {
        return repository.getAllProductInfo(lat,lon);
    }
}

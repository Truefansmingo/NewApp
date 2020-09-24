package com.flagcamp.secondhands.ui.fav;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;

import java.util.List;

public class FavViewModel extends ViewModel {
    private final ProductRepository repository;

    public FavViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    public  LiveData<List<Product>> getFavProductList(int uid){
        return repository.getFavProductList(uid);
    }

    public void deleteFavProduct(int id, Product product){
        repository.deleteFavProduct(id,product);
    }


}

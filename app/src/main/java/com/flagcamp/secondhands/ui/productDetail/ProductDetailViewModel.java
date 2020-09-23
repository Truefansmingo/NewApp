package com.flagcamp.secondhands.ui.productDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.ProductResponse;
import com.flagcamp.secondhands.repository.ProductRepository;

public class ProductDetailViewModel extends ViewModel {

    private final ProductRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public ProductDetailViewModel(ProductRepository productRepository){
        this.repository = productRepository;
    }

    public void setSearchInput(String query){
        searchInput.setValue(query);
    }

    public LiveData<ProductResponse> getProducts(){
        return Transformations.switchMap(searchInput, repository::getProducts);
    }
}

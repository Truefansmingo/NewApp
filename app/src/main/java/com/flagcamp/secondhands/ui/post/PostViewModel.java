package com.flagcamp.secondhands.ui.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;

public class PostViewModel extends ViewModel {
    private final ProductRepository repository;

    public PostViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    public void postProduct(Product product) {
        repository.postProduct(product);
    }
}

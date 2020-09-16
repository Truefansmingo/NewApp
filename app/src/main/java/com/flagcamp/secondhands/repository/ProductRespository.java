package com.flagcamp.secondhands.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.model.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductRespository {
    public LiveData<ProductResponse> getProducts(String query){
        MutableLiveData<ProductResponse> everyThingLiveData = new MutableLiveData<>();
        List<Product> products = new ArrayList<>();
        String uTI = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/1200px-Donald_Trump_official_portrait.jpg";
        for(int i = 0; i < 10; i++){
            Product temp = new Product("seller: " + i, "description: " + i, "postedAt: " + i, "title: " + i, "price: " + i, "url", uTI, false);
            products.add(temp);
        }
        ProductResponse p = new ProductResponse(products);
        everyThingLiveData.setValue(p);
        return everyThingLiveData;
    }
}

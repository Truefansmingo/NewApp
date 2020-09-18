package com.flagcamp.secondhands.ui.fav;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.data.DummyData;
import com.flagcamp.secondhands.data.Product;
import com.flagcamp.secondhands.data.User;

import java.util.List;

public class FavViewModel extends ViewModel {
//    private final ProductsRepository repository;
//
    //TODO: how to initalize the constructor

    //    public SaveViewModel(NewsRepository repository) {
//        this.repository = repository;
//    }
//
//    public boolean deleteSavedArticle(Article article) {
//        return repository.deleteSavedArticle(article);
//    }
//
//    public List<Product> getAllSavedProducts(){
//        return List<Product>;
//    }
    DummyData data;
    public FavViewModel(DummyData data){
        this.data = data;
    }
    public LiveData<List<Product>> getFavProductList(int id){
        List<Product> list = data.getFavProducts(id);
        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return  res;
    }
    public boolean deleteFavProduct(int id, Product product){
        return data.deleteFav(id,product);
    }
}

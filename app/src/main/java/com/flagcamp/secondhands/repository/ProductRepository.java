package com.flagcamp.secondhands.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.model.Product;

import java.util.List;

public class ProductRepository {

    private final DummyData data;
    private AsyncTask asyncTask;

    public ProductRepository(Context context){
        data = new DummyData();
    }

    public LiveData<List<Product>> getFavProductList(int id){
        List<Product> list = data.getFavProducts(id);
        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return  res;
    }
    public void deleteFavProduct(int id, Product product){
        data.deleteFav(id,product);
//        AsyncTask.execute(
//                ()->data.deleteFav(id,product));
    }
    public void onCancel() {
        if(asyncTask != null) {
            asyncTask.cancel(true);
        }
    }
}

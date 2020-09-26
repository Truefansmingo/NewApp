/*
This class is in charge of providing data.
METHOD getProducts return a livedata of ProductResponse, which containing a list of products.
In the future we need to call Apis implemented by our backend team to generate data.
For now I build a method called generateDummyProductDataForHomePage to generate data for testing home page and product detail page,
this method returns a ProductResponse and is called by getProducts.
This ProductResponse has 8 fields:
    List<Product> products : all 70 product objects
    List<Product> productsCat0 :　10 product objects of category0
    ...
    List<Product> productsCat6 :　10 product objects of category6

if we decide to use same repository like this, I believe you can either build your own dataApi or call mine, FOR NOW.
 */
package com.flagcamp.secondhands.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.model.ProductResponse;
import com.flagcamp.secondhands.model.SearchResponse;
import com.flagcamp.secondhands.network.Api;
import com.flagcamp.secondhands.network.RetrofitClient;
import com.flagcamp.secondhands.ui.search.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final OkHttpClient client = new OkHttpClient();
    private final DummyData data;
    private AsyncTask asyncTask;
    private final Api api;

    public ProductRepository(Context context){
        data = new DummyData();
        api = RetrofitClient.newInstance(context).create(Api.class);
    }

    public LiveData<List<Product>> getFavProductList(int id){
        List<Product> list = data.getFavProducts(id);
        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return  res;
    }
    public void deleteFavProduct(int id, Product product){
        data.deleteFav(id,product);
    }
    public void onCancel() {
        if(asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    public LiveData<ProductResponse> getProducts(String query){
        MutableLiveData<ProductResponse> everyThingLiveData = new MutableLiveData<>();
        everyThingLiveData.setValue(generateDummyProductDataForHomePage());
        return everyThingLiveData;
    }

//    // Test search feature with dummy data
//    public LiveData<ProductResponse> searchProducts(SearchViewModel.Cell cell){
//        MutableLiveData<ProductResponse> everyThingLiveData = new MutableLiveData<>();
//        everyThingLiveData.setValue(generateDummyProductDataForHomePage());
//        return everyThingLiveData;
//    }

    public LiveData<SearchResponse> searchProducts(SearchViewModel.Cell cell){
        MutableLiveData<SearchResponse> everyThingLiveData = new MutableLiveData<>();
        api.search(cell.searchInput, cell.categoryInput, cell.locationInput)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            everyThingLiveData.setValue(response.body());
                        } else {
                            everyThingLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        everyThingLiveData.setValue(null);
                    }
                });
        return everyThingLiveData;
    }

    public ProductResponse generateDummyProductDataForHomePage(){
        List<List<Product>> tempCategories = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            tempCategories.add(new ArrayList<>());
        }
        List<Product> products = new ArrayList<>();
        String img1 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump1.jpg?alt=media&token=a7da5dad-2354-4979-b155-75fb1e9e845d";
        String img2 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump2.jpg?alt=media&token=d4eccd9e-678a-49f3-abd8-739fa62ed275";
        String img3 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump3.jpg?alt=media&token=50d33d62-d9e2-42b7-9084-62743b06fb5b";
        String img4 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump4.jpg?alt=media&token=891a1192-3b69-472c-9767-7d745987a9f5";
        String img5 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump5.jpg?alt=media&token=4978e837-4d5e-4794-830f-dc15e8ad198b";
        String img6 = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump6.jpg?alt=media&token=557cb42f-613d-44cf-9f21-c554bcdaedd1";
        List<String> urlToImage1 = new ArrayList<>();
        urlToImage1.add(img1);
        urlToImage1.add(img2);
        urlToImage1.add(img3);
        List<String> urlToImage2 = new ArrayList<>();
        urlToImage2.add(img4);
        urlToImage2.add(img5);
        urlToImage2.add(img6);

        for(int i = 0; i < 70; i++){
            List<String> urlToImage;
            if(i % 2 == 0){
                urlToImage = urlToImage1;
            }else{
                urlToImage = urlToImage2;
            }
            Product temp = new Product("seller: " + i, "description: " + i, "postedAt: " + i, "title: " + i, "price: " + i, urlToImage, ""+i, false, "location: " + i);
            tempCategories.get(i / 10).add(temp);
            products.add(temp);
        }

        return new ProductResponse(products,tempCategories.get(0), tempCategories.get(1), tempCategories.get(2), tempCategories.get(3), tempCategories.get(4), tempCategories.get(5), tempCategories.get(6));
    }
}

package com.flagcamp.secondhands.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyData {
    Map<Integer, List<Product>> map;
    private final String tag =  DummyData.this.getClass().getSimpleName();
    //data source
    //String seller, String description, String title, String price
    Product p1 = new Product(
            "Sell_One","This is a laptop","Laptop","$1000");
    Product p2 = new Product(
            "Sell_Two","This is a table","Table","$50");

    Product p3 = new Product(
            "Sell_Three","This is a watch","Watch","$100");


    User user= new User();
    private final int id1 = user.addUser();
    private final int id2 = user.addUser();
    //create dummy data
    //user one has p1,p2 fav; user two has p3 fav
    public DummyData(){
        map = new HashMap<>();
        map.put(id1, new ArrayList<>());
        map.put(id2, new ArrayList<>());
        for(int i = 0; i <10; i++){
            map.get(id1).add(p1);
            map.get(id1).add(p2);
        }
        for(int i = 0; i <10; i++){
            map.get(id2).add(p3);
        }

    }

    //public method:
    public List<Product> getFavProducts(int userId){
        return map.get(userId);
    }

    public boolean deleteFav(int userId, Product product){
        if(!map.containsKey(userId)) {
            Log.d(tag, "no userId: "+ userId );
            return false;
        }
        List<Product> productList = map.get(userId);
        productList.remove(product);
        Log.d(tag, "List size is "+ productList.size() );
        return true;
    }



}




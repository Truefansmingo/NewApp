package com.flagcamp.secondhands.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyData {
    Map<String, List<Product>> map;
    private final String tag =  DummyData.this.getClass().getSimpleName();
    //data source
    //String seller, String description, String title, String price
    Product p1 = new Product(
            "Sell_One","This is a laptop","New York", 40.7127837,-74.0059413,"Laptop","$1000");
    Product p2 = new Product(
            "Sell_Two","This is a table","Los Angeles",34.0522342,-118.2436849,"Table","$50");

    Product p3 = new Product(
            "Sell_Three","This is a watch","Houston",29.7604267,-95.3698028,"Watch","$100");

    Product p4 = new Product(
            "Sell_Three","This is a shoe","St. Petersburg",27.773056,-82.64,"Shoe","$10");

    Product p5 = new Product(
            "Sell_Two","This is a car","Nashville-Davidson",36.1626638,-86.7816016,"Car","$2500");


    User user1= new User("1", "wleijf");
    User user2 = new User("2", "sdf");
    private final String id1 = user1.userId;
    private final String  id2 = user2.userId;
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




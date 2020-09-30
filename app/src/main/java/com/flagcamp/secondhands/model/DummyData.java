package com.flagcamp.secondhands.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyData {
    Map<Integer, List<Product>> map;
    List<Product> products;
    private final String tag =  DummyData.this.getClass().getSimpleName();
    //data source
    //String seller, String description, String title, String price
    Product p1 = new Product(
            "Sell_One","This is a laptop","New York", 40.7127837,-74.0059413,"Laptop","$1000");
    Product p2 = new Product(
            "Sell_Two","This is a table","Los Angeles",34.0522342,-118.2436849,"Table","$50");

    Product p3 = new Product(
            "Sell_Three","This is a watch","Bay Area",37.386051,-122.0835855,"Watch","$100");

    Product p4 = new Product(
            "Sell_Three","This is a shoe","Bay Area",37.18,-122.28,"Shoe","$10");

    Product p5 = new Product(
            "Sell_Two","This is a car","Bay Area",37.58,-121.88,"Car","$2500");


    User user = new User();
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
       products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);

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
    public List<Product> getProductInfo(double lat, double lon){
        //just render the markers in the scope that map view
        double minLon = lon-0.25;
        double maxLon = lon+0.25;
        double minLat = lat-0.25;
        double maxLat = lat+0.25;

        List<Product> res = new ArrayList<>();
        for(Product p: products){
            if(p.lat >=minLat && p.lat <= maxLat && p.lon >=minLon && p.lon <=maxLon){
                res.add(p);
            }
        }
        return res;
    }




}




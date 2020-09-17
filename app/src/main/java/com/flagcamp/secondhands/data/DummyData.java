package com.flagcamp.secondhands.data;

import android.support.v4.app.INotificationSideChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyData {
    Map<Integer, List<Product>> map;
    //data source
    Product p1 = new Product(
            1, "Sell_One","This is a laptop","San Francisco","Laptop","$1000","","",true, "available");
    Product p2 = new Product(
            2, "Sell_Two","This is a table","NewYork","Table","$50","","",true, "available");

    Product p3 = new Product(
            3,"Sell_Tree","This is a watch","San Jose","Watch","$100","","",true , "available");

    User user= new User();
    private final int id1 = user.addUser();
    private final int id2 = user.addUser();
    //create dummy data
    //user one has p1,p2 fav; user two has p3 fav
   public DummyData(){
        map = new HashMap<>();
        map.put(id1, new ArrayList<>());
        map.put(id2, new ArrayList<>());
        map.get(id1).add(p1);
        map.get(id1).add(p2);
        map.get(id2).add(p3);

    }

    //public method:
    public List<Product> getFavProducts(int userId){
        return map.get(userId);
    }

    public boolean deleteFav(int userId, Product product){
       if(!map.containsKey(userId)) {
           return false;
       }
       List<Product> productList = map.get(userId);
       productList.remove(product);
       return true;
    }



}




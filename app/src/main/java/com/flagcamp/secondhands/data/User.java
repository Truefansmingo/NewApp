package com.flagcamp.secondhands.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
   int id;

    User(){
        id = 0;
    }
    public int addUser(){
        int res = id;
        id++;
        return res;
    }


}

package com.flagcamp.secondhands.model;


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

package com.flagcamp.secondhands.model;

import java.io.Serializable;

public class User implements Serializable {
    public String userId;
    public String name;
    public String photoUrl;
    public String email;
    public String rating;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public User(String userId, String name, String photoUrl, String email, String rating) {
        this.userId = userId;
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
        this.rating = rating;
    }
}

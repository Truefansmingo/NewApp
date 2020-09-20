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
}

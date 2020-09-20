package com.flagcamp.secondhands;

public class CurrentUserSingleton {
    private static CurrentUserSingleton instance = new CurrentUserSingleton();
    private String userId;
    private String userName;
    private String photoUrl;
    private String email;
    private String rating;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton getInstance() {
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

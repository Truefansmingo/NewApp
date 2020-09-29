package com.flagcamp.secondhands.model;

import com.flagcamp.secondhands.CurrentUserSingleton;

import java.util.List;
import java.util.Objects;

public class Product {
    public String seller; // 搜索到自己post的商品
    public String description;

    public String location; // State: NJ CA NY
    public double lat;
    public double lon;

    public String postedAt; // No need;
    public String title;
    public String price;
    public String productId;
    //public final String id;

    public boolean status; // true for available, false for sold out

    public String urlToImageFolder;
    public List<String> urlToImage;

    public boolean favorite;
    public String category; //change to enum later;


    public Product(String seller, String description, String postedAt, String title, String price, String urlToImageFolder, String urlToImage, String id, boolean favorite) {
        this.seller = seller;
        this.description = description;
        this.postedAt = postedAt;
        this.title = title;
        this.price = price;
        this.urlToImageFolder = urlToImageFolder;
       // this.id = id;
        this.favorite = favorite;
    }

    //this product constructor used in post product feature; //change to enum later;
    public Product(String seller, String productId, String title, String price, String description, String urlToImageFolder){
        this.seller = seller;
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.description = description;
        this.urlToImageFolder = urlToImageFolder;
    }

    public Product(){

    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrlToImageFolder() {
        return urlToImageFolder;
    }

    public void setUrlToImageFolder(String urlToImageFolder) {
        this.urlToImageFolder = urlToImageFolder;
    }

    public List<String> getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(List<String> urlToImage) {
        this.urlToImage = urlToImage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    //change to enum later;
    public String getCategory() {
        return category;
    }

    //change to enum later;
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                ", postedAt='" + postedAt + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(seller, product.seller) &&
                Objects.equals(description, product.description) &&
                Objects.equals(postedAt, product.postedAt) &&
                Objects.equals(title, product.title) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller, description, postedAt, title, price);
    }
}


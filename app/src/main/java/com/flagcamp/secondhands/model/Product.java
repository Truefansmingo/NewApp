/*
This class is the product model including all the fields we need.
basic methods including constructer(), toString(), equals(), and hashCode() are already generated.
TODO(NOT NECESSARY): 1. change location field and category field to enum.
                    2. replace constructer with a builder
 */

package com.flagcamp.secondhands.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {
    public String seller; // 搜索到自己post的商品
    public String description;
    public String location; // State: NJ CA NY
    public double lat;
    public double lon;
    public long postedAt; // (TBD)
    public String title;
    public String price;
    public final String id;
    public boolean status = true; // true for available, false for sold out
    public String urlToImageFolder;
    public List<String> urlToImage;
    public boolean favorite = false;
    public String category; //TBD
    public String rating;

    public Product(String seller, String description, String title, String price, List<String> urlToImage, String id, boolean favorite, String location) {
        this.seller = seller;
        this.description = description;
        this.postedAt = new Date().getTime();
        this.title = title;
        this.price = price;
        this.urlToImage = urlToImage;
        this.id = id;
        this.favorite = favorite;
        this.location = location;
    }

    public Product(String seller, String description, String location, double lat, double lon, String title, String price) {
        this.seller = seller;
        this.description = description;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.postedAt = new Date().getTime();
        this.title = title;
        this.price = price;
        id = "234";
        status = true;
        urlToImageFolder = null;
        urlToImage = null;
        favorite = true;
        category = "TBD";
    }

    public Product(String id){ // for post feature
        this.id = id;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrlToImageFolder(String urlToImageFolder) {
        this.urlToImageFolder = urlToImageFolder;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

/*


 */

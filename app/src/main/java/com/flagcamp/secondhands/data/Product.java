package com.flagcamp.secondhands.data;

import java.util.List;
import java.util.Objects;

public class Product {

    public String seller; // 搜索到自己post的商品
    public String description;

    public String location; // State: NJ CA NY
    public double lat;
    public double lon;

    public String postedAt; // (TBD)
    public String title;
    public String price;
    public int id = 0;

  //  public boolean status; // true for available, false for sold out
    public String status;
    public String urlToImageFolder;
    public List<String> urlToImage;

    public boolean favorite;
    public String category; //TBD

    public Product(String seller, String description, String title, String price) {
        this.seller = seller;
        this.description = description;
        location = "CA";
        lat = 0.0;
        lon = 0.0;
        postedAt = "09082020";
        this.title = title;
        this.price = price;
        id++;
        status = "Available";
        urlToImageFolder = null;
        urlToImage = null;
        favorite = true;
        category = "TBD";
    }

    @Override
    public String toString() {
        return "Product{" +
                "seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", postedAt='" + postedAt + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", favorite=" + favorite +
                ", category='" + category + '\'' +
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


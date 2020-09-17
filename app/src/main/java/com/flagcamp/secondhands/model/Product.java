package com.flagcamp.secondhands.model;

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
    public final String id;

    public boolean status; // true for available, false for sold out

    public String urlToImageFolder;
    public List<String> urlToImage;

    public boolean favorite;
    public Enum category; //TBD

    public Product(String seller, String description, String postedAt, String title, String price, String urlToImageFolder, String urlToImage, String id, boolean favorite) {
        this.seller = seller;
        this.description = description;
        this.postedAt = postedAt;
        this.title = title;
        this.price = price;
        this.urlToImageFolder = urlToImageFolder;
        this.id = id;
        this.favorite = favorite;
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

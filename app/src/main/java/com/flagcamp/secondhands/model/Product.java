package com.flagcamp.secondhands.model;

import java.util.Objects;

public class Product {
    public String seller;
    public String description;
    public String postedAt;
    public String title;
    public String price;

    public String url;
    public String urlToImage;
    public boolean favorite;

    public Product(String seller, String description, String postedAt, String title, String price, String url, String urlToImage, boolean favorite) {
        this.seller = seller;
        this.description = description;
        this.postedAt = postedAt;
        this.title = title;
        this.price = price;
        this.url = url;
        this.urlToImage = urlToImage;
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

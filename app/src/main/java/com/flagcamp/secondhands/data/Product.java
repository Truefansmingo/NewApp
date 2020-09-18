package com.flagcamp.secondhands.data;

import java.util.Objects;

public class Product {

    private int id;
    public String seller;
    public String description;
    public String postedAt;
    public String title;
    public String price;

    public String url;
    public String urlToImage;
    public boolean favorite;

    public String status;

    public Product(int id, String seller, String description, String postedAt, String title, String price, String url, String urlToImage, boolean favorite, String status) {
        this.id = id;
        this.seller = seller;
        this.description = description;
        this.postedAt = postedAt;
        this.title = title;
        this.price = price;
        this.url = url;
        this.urlToImage = urlToImage;
        this.favorite = favorite;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                ", postedAt='" + postedAt + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", favorite=" + favorite +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


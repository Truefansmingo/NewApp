/*
This is a class I use to encapsulate product objects.
Will be used in Class ProductRepository, whose method getProducts will be called in HomeViewModel and ProductDetailViewModel

Basically it has 8 fields, the first one is a list containing all the product objects,
the others are lists containing product objects of a certain categories.

I build it because my homepage implementation needs data of product objects of all categories seperated
 */
package com.flagcamp.secondhands.model;

import java.util.List;
import java.util.Objects;

public class ProductResponse {
    public List<Product> products;
    public List<Product> productsCat0;
    public List<Product> productsCat1;
    public List<Product> productsCat2;
    public List<Product> productsCat3;
    public List<Product> productsCat4;
    public List<Product> productsCat5;
    public List<Product> productsCat6;

    public List<Product> getCat(int i){
        if(i == 0){
            return productsCat0;
        }
        if(i == 1){
            return productsCat1;
        }
        if(i == 2){
            return productsCat2;
        }
        if(i == 3){
            return productsCat3;
        }
        if(i == 4){
            return productsCat4;
        }
        if(i == 5){
            return productsCat5;
        }
        return productsCat6;
    }

    public ProductResponse(List<Product> products, List<Product> productsCat0, List<Product> productsCat1, List<Product> productsCat2, List<Product> productsCat3, List<Product> productsCat4, List<Product> productsCat5, List<Product> productsCat6) {
        this.products = products;
        this.productsCat0 = productsCat0;
        this.productsCat1 = productsCat1;
        this.productsCat2 = productsCat2;
        this.productsCat3 = productsCat3;
        this.productsCat4 = productsCat4;
        this.productsCat5 = productsCat5;
        this.productsCat6 = productsCat6;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "products=" + products +
                ", productsCat0=" + productsCat0 +
                ", productsCat1=" + productsCat1 +
                ", productsCat2=" + productsCat2 +
                ", productsCat3=" + productsCat3 +
                ", productsCat4=" + productsCat4 +
                ", productsCat5=" + productsCat5 +
                ", productsCat6=" + productsCat6 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponse that = (ProductResponse) o;
        return Objects.equals(products, that.products) &&
                Objects.equals(productsCat0, that.productsCat0) &&
                Objects.equals(productsCat1, that.productsCat1) &&
                Objects.equals(productsCat2, that.productsCat2) &&
                Objects.equals(productsCat3, that.productsCat3) &&
                Objects.equals(productsCat4, that.productsCat4) &&
                Objects.equals(productsCat5, that.productsCat5) &&
                Objects.equals(productsCat6, that.productsCat6);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, productsCat0, productsCat1, productsCat2, productsCat3, productsCat4, productsCat5, productsCat6);
    }
}

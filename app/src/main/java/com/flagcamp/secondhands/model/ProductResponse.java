package com.flagcamp.secondhands.model;

import java.util.List;
import java.util.Objects;

public class ProductResponse {
    public List<Product> products;

    public ProductResponse(List<Product> products){
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponse that = (ProductResponse) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}

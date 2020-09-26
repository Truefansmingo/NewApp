package com.flagcamp.secondhands.model;

import java.util.List;
import java.util.Objects;

//public class SearchResponse {
//    public Integer totalResults;
//    public List<Product> products;
//    public String code;
//    public String message;
//    public String status;
//
//    @Override
//    public String toString() {
//        return "NewsResponse{" +
//                "totalResults=" + totalResults +
//                ", products=" + products +
//                ", code='" + code + '\'' +
//                ", message='" + message + '\'' +
//                ", status='" + status + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SearchResponse that = (SearchResponse) o;
//        return Objects.equals(totalResults, that.totalResults) &&
//                Objects.equals(products, that.products) &&
//                Objects.equals(code, that.code) &&
//                Objects.equals(message, that.message) &&
//                Objects.equals(status, that.status);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(totalResults, products, code, message, status);
//    }
//}

public class SearchResponse {
    public List<Product> products;

    @Override
    public String toString() {
        return "NewsResponse{" +
                "products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResponse that = (SearchResponse) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
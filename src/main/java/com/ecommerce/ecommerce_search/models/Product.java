package com.ecommerce.ecommerce_search.models;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String product_uuid;
    private String name;
    private String description;
    private String category;
    private String brand;
    private Double price;

}

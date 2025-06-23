package com.ecommerce.ecommerce_search.models;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String category;
    private String brand;
    private Double price;

}

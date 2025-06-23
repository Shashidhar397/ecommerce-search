package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;

import java.util.List;

public interface IFProductService {

    public List<Product> getAllProducts();

    public void addProduct(Product product);

}

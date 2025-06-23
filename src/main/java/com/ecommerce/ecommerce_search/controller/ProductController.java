package com.ecommerce.ecommerce_search.controller;

import com.ecommerce.ecommerce_search.models.ProductResponse;
import com.ecommerce.ecommerce_search.services.IFProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IFProductService productService;

    public ProductController(IFProductService productService) {
        this.productService = productService;
    }


    // Example method to get all products
     @GetMapping
     public List<ProductResponse> getAllProducts() {
         return productService.getAllProducts();
     }

    // Example method to get a product by ID
    // @GetMapping("/products/{id}")
    // public Product getProductById(@PathVariable String id) {
    //     return productService.getProductById(id);
    // }

}

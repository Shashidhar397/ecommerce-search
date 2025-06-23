package com.ecommerce.ecommerce_search.controller;

import com.ecommerce.ecommerce_search.models.Product;
import com.ecommerce.ecommerce_search.services.IFProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IFProductService productService;

    public ProductController(IFProductService productService) {
        this.productService = productService;
    }


    // Example method to get all products
     @GetMapping
     public List<Product> getAllProducts() {
         return productService.getAllProducts();
     }

     @PostMapping
     public String postMethodName(@RequestBody Product product) {
         this.productService.addProduct(product);
         return "Successfully added product with ID: " + product.getId();
     }
     

}

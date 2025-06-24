package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IFProductService {

    private final IFSearchService searchService;

    public ProductServiceImpl(IFSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Product> getAllProducts() {
        // Logic to fetch all products from the database or any data source
        try {
            this.searchService.searchProduct("*:*", 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately, maybe log them or rethrow as a custom exception
        }
        return new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        // Logic to add a product to the database or any data source
        try {
            this.searchService.indexProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately, maybe log them or rethrow as a custom exception
        }
    }

    // Additional methods for product-related operations can be added here
    
}

package com.ecommerce.ecommerce_search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_search.models.SearchRequest;
import com.ecommerce.ecommerce_search.models.SearchResponse;
import com.ecommerce.ecommerce_search.services.IFSearchService;
import com.ecommerce.ecommerce_search.services.SearchServiceFactory;

@RestController
@RequestMapping("/api")
public class SearchController {
    
    private final SearchServiceFactory searchServiceFactory;

    public SearchController(SearchServiceFactory searchServiceFactory) {
        this.searchServiceFactory = searchServiceFactory;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest request, @RequestParam(name = "serviceType", required = false) String serviceType) {
        try {
            IFSearchService searchService = this.searchServiceFactory.getSearchService(serviceType);
            return ResponseEntity.ok(searchService.searchProducts(request));
        } catch (org.apache.solr.client.solrj.SolrServerException | java.io.IOException e) {
            e.printStackTrace();
            // You can customize the error response as needed
            return ResponseEntity.status(500).body(null);
        }
    }

}

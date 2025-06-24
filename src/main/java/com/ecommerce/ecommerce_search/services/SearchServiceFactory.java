package com.ecommerce.ecommerce_search.services;

import org.springframework.stereotype.Service;

@Service
public class SearchServiceFactory {
    
    private final SolrServiceImpl solrService;

    public SearchServiceFactory(SolrServiceImpl solrService) {
        this.solrService = solrService;
    }

    public IFSearchService getSearchService(String serviceType) {
        return this.solrService; // Currently, only SolrServiceImpl is implemented
    }


}

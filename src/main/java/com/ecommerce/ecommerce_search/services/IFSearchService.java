package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;
import com.ecommerce.ecommerce_search.models.SearchRequest;
import com.ecommerce.ecommerce_search.models.SearchResponse;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface IFSearchService {

    boolean indexProduct(Product product);

    boolean searchProduct(String query, int page, int size) throws SolrServerException, IOException;

    SearchResponse searchProducts(SearchRequest request) throws SolrServerException, IOException;

}

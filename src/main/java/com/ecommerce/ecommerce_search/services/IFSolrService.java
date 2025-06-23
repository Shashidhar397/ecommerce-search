package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface IFSolrService {

    boolean indexProduct(Product product);

    boolean searchProduct(String query, int page, int size) throws SolrServerException, IOException;
}

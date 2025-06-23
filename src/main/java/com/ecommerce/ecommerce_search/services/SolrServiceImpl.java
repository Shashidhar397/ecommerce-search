package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolrServiceImpl implements IFSolrService{

    private final SolrClient solrClient;
    public SolrServiceImpl(SolrClient solrClient) {
        this.solrClient = solrClient;
    }


    @Override
    public boolean indexProduct(Product product) {
        return false;
    }

    @Override
    public boolean searchProduct(String query, int page, int size) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        solrQuery.setStart((page - 1) * size);
        solrQuery.setRows(size);

        QueryResponse response = this.solrClient.query("products", solrQuery);
        response.getResults().forEach(System.out::println);
        // Process response.getResults() as needed

        return false;
    }
}
